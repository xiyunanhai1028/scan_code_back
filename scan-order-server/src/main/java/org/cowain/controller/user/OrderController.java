package org.cowain.controller.user;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.OrderPaymentDTO;
import org.cowain.dto.OrderSubmitDTO;
import org.cowain.dto.OrdersPageQueryDTO;
import org.cowain.entity.OrderDetail;
import org.cowain.entity.Orders;
import org.cowain.result.PageResult;
import org.cowain.result.Result;
import org.cowain.service.OrderService;
import org.cowain.vo.OrderPaymentVO;
import org.cowain.vo.OrderSubmitVO;
import org.cowain.vo.QueueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "C端-订单相关接口")
@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("用户下单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        log.info("用户下单参数：{}", orderSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(orderSubmitDTO);
        return Result.success(orderSubmitVO);
    }


    @ApiOperation("预支付接口")
    @PostMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrderPaymentDTO orderPaymentDTO) throws Exception {
        log.info("订单支付参数：{}", orderPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(orderPaymentDTO);
        return Result.success(orderPaymentVO);
    }

    @ApiOperation("分页查询订单信息")
    @GetMapping("/list")
    public Result<PageResult> list(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("分页查询订单信息:{}",ordersPageQueryDTO);
        PageResult page = orderService.queryListByUserId(ordersPageQueryDTO);
        return Result.success(page);
    }

    @ApiOperation("根据订单ID查询订单详情")
    @GetMapping("/detail/{id}")
    public Result<List<OrderDetail>> detail(@PathVariable Long id){
        log.info("根据订单ID查询订单详情:{}",id);
        List<OrderDetail> list = orderService.queryOrderDetailListById(id);
        return Result.success(list);
    }

    @ApiOperation("根据订单号与桌位号查询排队信息")
    @GetMapping("/queue")
    public Result<QueueVO> queue(Long orderId) {
        log.info("根据订单号与桌位号查询排队信息:{},{}", orderId);
//        QueueVO vo = orderService.getQueueInfoBySitNumAndId(sitNum, orderId);
        QueueVO vo = orderService.getQueueInfoByOrderId(orderId);
        return Result.success(vo);
    }

    @ApiOperation("用户取消订单")
    @PutMapping("/cancel/{id}")
    public Result<String> cancel(@PathVariable("id") Long id) throws Exception {
        orderService.userCancelById(id);
        return Result.success("订单已取消");
    }

    @ApiOperation("获取用户订单")
    @GetMapping("/order")
    public Result<Orders> getOrders(Integer sitNum){
        log.info("获取用户订单:{}",sitNum);
        Orders orders = orderService.getQueueInfoBySitNumAndUers(sitNum);
        return Result.success(orders);
    }
}
