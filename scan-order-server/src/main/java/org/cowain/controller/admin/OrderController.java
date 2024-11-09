package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.dto.OrdersPageQueryDTO;
import org.cowain.result.PageResult;
import org.cowain.result.Result;
import org.cowain.service.OrderService;
import org.cowain.vo.OrderStatisticsVO;
import org.cowain.vo.QueueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("订单搜索")
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单搜索:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @ApiOperation("根基订单编号更新订单状态")
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, String number) {
        log.info("根基订单编号更新订单状态:{},{}", status, number);
        orderService.updateOrderStatusByNumber(status, number);
        return Result.success("更新成功");
    }

}
