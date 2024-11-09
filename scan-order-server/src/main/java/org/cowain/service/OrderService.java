package org.cowain.service;

import org.cowain.dto.OrderPaymentDTO;
import org.cowain.dto.OrderSubmitDTO;
import org.cowain.dto.OrdersPageQueryDTO;
import org.cowain.entity.OrderDetail;
import org.cowain.entity.Orders;
import org.cowain.result.PageResult;
import org.cowain.vo.OrderPaymentVO;
import org.cowain.vo.OrderStatisticsVO;
import org.cowain.vo.OrderSubmitVO;
import org.cowain.vo.QueueVO;

import java.util.List;

public interface OrderService {
    //用户下单
    OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO);

    //订单支付
    OrderPaymentVO payment(OrderPaymentDTO orderPaymentDTO) throws Exception;

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    //根据用户ID查询用户订单信息
    PageResult queryListByUserId(OrdersPageQueryDTO ordersPageQueryDTO);

    //根据订单ID查询订单详情
    List<OrderDetail> queryOrderDetailListById(Long id);

    //支付成功回调
    void paySuccess(String outTradeNo);

    //根据订单编号更新订单状态
    void updateOrderStatusByNumber(Integer status, String number);

    QueueVO getQueueInfoBySitNumAndId(Integer sitNum, String orderId);
    QueueVO getQueueInfoByOrderId(Long orderId);

    //用户取消订单
    void userCancelById(Long id) throws Exception;

    Orders getQueueInfoBySitNumAndUers(Integer sitNum);
}
