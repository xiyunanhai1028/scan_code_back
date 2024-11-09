package org.cowain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.cowain.constant.MessageConstant;
import org.cowain.context.BaseContext;
import org.cowain.dto.OrderPaymentDTO;
import org.cowain.dto.OrderSubmitDTO;
import org.cowain.dto.OrdersPageQueryDTO;
import org.cowain.entity.*;
import org.cowain.exception.OrderBusinessException;
import org.cowain.exception.ShoppingCartBusinessException;
import org.cowain.mapper.*;
import org.cowain.result.PageResult;
import org.cowain.service.OrderService;
import org.cowain.utils.WeChatPayUtils;
import org.cowain.vo.*;
import org.cowain.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatPayUtils weChatPayUtils;
    @Autowired
    private WebSocketServer webSocketServer;

    //用户下单
    @Transactional
    public OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO) {
        int sitNum = orderSubmitDTO.getSitNum();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setSitNum(sitNum);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //1.查询该桌位上是否有待支付的订单
        Orders orders = orderMapper.queryOrderBySitNumAndPayStatus(sitNum, Orders.UN_PAID);

        if (orders == null) {
            //2.向订单表插入1条数据
            orders = new Orders();
            BeanUtils.copyProperties(orderSubmitDTO, orders);
            orders.setNumber(String.valueOf(System.currentTimeMillis()));//订单号
            HashSet<Long> setUserIds = new HashSet<>();
            setUserIds.add(userId);
            for (ShoppingCart cart : shoppingCartList) {
                setUserIds.add(cart.getUserId());
            }
            String userIds = "";
            for (Long id : setUserIds) {
                userIds += id + ",";
            }
            orders.setUserIds(userIds);
            orders.setUserId(userId);
            orders.setStatus(Orders.PENDING_QUEUE);
            orders.setOrderTime(LocalDateTime.now());//下单时间
            orders.setPayStatus(Orders.UN_PAID);//未支付
            orders.setPhone(user.getPhone());
            orderMapper.insert(orders);
        } else {
            BigDecimal amount = orders.getAmount().add(orderSubmitDTO.getAmount());
            orders.setAmount(amount);
            if (!orders.getUserIds().contains(String.valueOf(userId))) {
                orders.setUserIds(orders.getUserIds() + "," + userId);
            }
            orderMapper.update(orders);
        }

        //3.插入订单明细
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());//设置当前订单明细关联的订单ID
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        //4.清空当前用户的购物车-根据桌位号
        shoppingCartMapper.deleteBySitNum(orderSubmitDTO.getSitNum());

        //5.封装VO返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .build();


        //发送通知到管理端-有客户下单
        Map map = new HashMap();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + orders.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));

        return orderSubmitVO;
    }

    //支付订单
    public OrderPaymentVO payment(OrderPaymentDTO orderPaymentDTO) throws Exception {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);
        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtils.pay(
                orderPaymentDTO.getOrderNumber(),//商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "测试支付描述信息",//商品描述
                user.getOpenid()//微信用户的openid
        );
        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
        return vo;
    }

    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Integer status = ordersPageQueryDTO.getStatus();
        if (status != null && status == 0) {
            ordersPageQueryDTO.setStatus(null);
        }
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

        // 部分订单状态，需要额外返回订单菜品信息，将Orders转化为OrderVO
        List<OrderVO> orderVOList = getOrderVOList(page);

        return new PageResult(page.getTotal(), orderVOList);
    }


    /**
     * 根据订单id获取菜品信息字符串
     *
     * @param orders
     * @return
     */
    private String getOrderDishesStr(Orders orders) {
        // 查询订单菜品详情信息（订单中的菜品和数量）
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // 将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3；）
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());

        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }

    public OrderStatisticsVO statistics() {
        // 根据状态，分别查询出待接单、待派送、派送中的订单数量
        Integer toBeConfirmed = orderMapper.countStatus(Orders.PENDING_QUEUE);
        Integer confirmed = orderMapper.countStatus(Orders.MAKING_MEAL);
        Integer deliveryInProgress = orderMapper.countStatus(Orders.DINING_OUT);

        // 将查询出的数据封装到orderStatisticsVO中响应
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }

    @Override
    public PageResult queryListByUserId(OrdersPageQueryDTO ordersPageQueryDTO) {
        Long userId = BaseContext.getCurrentId();
        Integer status = ordersPageQueryDTO.getStatus();
        if (status == 0) {
            ordersPageQueryDTO.setStatus(null);
        }
        //开启分页
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> orders = orderMapper.pageQuery(ordersPageQueryDTO);
        // 需要返回订单菜品信息，自定义OrderVO响应结果
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Orders order : orders) {
            String userIds = order.getUserIds();
            if (userIds.indexOf(String.valueOf(userId)) > -1) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);
                String orderDishes = getOrderDishesStr(order);

                // 将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                List<OrderDetail> orderDetail = orderDetailMapper.getByOrderId(order.getId());
                orderVO.setOrderDetailList(orderDetail);

                orderVOList.add(orderVO);
            }
        }
        return new PageResult(orderVOList.size(), orderVOList);
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        // 需要返回订单菜品信息，自定义OrderVO响应结果
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);

                // 将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    @Override
    public List<OrderDetail> queryOrderDetailListById(Long id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(id);
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
        return orderDetailList;
    }

    //支付成功回调
    public void paySuccess(String outTradeNo) {
        //根据订单号查询当前订单，修改状态
        Orders orders = orderMapper.getOrdersByNumber(outTradeNo);
        orders.setPayStatus(Orders.PAID);
        orders.setCheckoutTime(LocalDateTime.now());
        orderMapper.update(orders);

        //发送通知到管理端-支付成功
        Map map = new HashMap();
        map.put("type", 2);
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + orders.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }


    public void updateOrderStatusByNumber(Integer status, String number) {
        //1.更新订单编号
        Orders orders = orderMapper.getOrdersByNumber(number);
        orders.setStatus(status);
        orderMapper.update(orders);
    }


    public QueueVO getQueueInfoBySitNumAndId(Integer sitNum, String orderId) {
        Orders orders = orderMapper.queryOrderBySitNumAndId(sitNum, orderId);
        List<Orders> ordersList = orderMapper.getOrdersByTimeLT(orders.getOrderTime(), LocalDateTime.now());
        QueueVO vo = QueueVO.builder()
                .status(orders.getStatus())
                .queueNum(ordersList.size())
                .build();
        return vo;
    }

    @Override
    public QueueVO getQueueInfoByOrderId(Long orderId) {
        Orders orders = orderMapper.getOrderById(orderId);

        List<Orders> orderList = orderMapper.getOrdersByStatus(Orders.PENDING_QUEUE);
        int num = 0;
        for (Orders o : orderList) {
            Long id = o.getId();
            if (id == orderId) break;
            num++;
        }
        QueueVO vo = QueueVO.builder()
                .status(orders.getStatus())
                .queueNum(num)
                .build();
        return vo;
    }

    //用户取消订单
    public void userCancelById(Long id) {
        //根据ID查询订单
        Orders orders = orderMapper.getOrderById(id);
        //校验订单是否存在
        if (orders == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        //订单状态 1排队 2做餐 3出餐 4完成 5取消 6已退款 7已评论
        if (orders.getStatus() > 1) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        //判断用户是否已经支付了
        if (orders.getPayStatus() == Orders.PAID) {
            //调用微信支付退款接口
            try {
                weChatPayUtils.refund(
                        orders.getNumber(), //商户订单号
                        orders.getNumber(), //商户退款单号
                        new BigDecimal(0.01),//退款金额，单位 元
                        new BigDecimal(0.01));//原订单金额
            } catch (Exception e) {
                throw new OrderBusinessException(MessageConstant.CANCEL_ORDER_FAIL);
            }
            orders.setPayStatus(Orders.REFUND);
            //修改订单状态
            orders.setStatus(Orders.REFUNDED);
        } else {
            orders.setStatus(Orders.CANCELLED);
        }
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    @Override
    public Orders getQueueInfoBySitNumAndUers(Integer sitNum) {
        Long userId = BaseContext.getCurrentId();
        Orders orders = orderMapper.queryOrderBySitNumAndUserId(sitNum, userId);
        if (orders == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        return orders;
    }
}
