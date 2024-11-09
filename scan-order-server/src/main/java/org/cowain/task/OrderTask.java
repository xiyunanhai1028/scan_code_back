package org.cowain.task;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.cowain.entity.Orders;
import org.cowain.mapper.OrderMapper;
import org.cowain.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务类，定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WebSocketServer webSocketServer;

//    /**
//     * 处理排队 每隔一分钟
//     */
//    @Scheduled(cron = "0 * * * * ?")//每分钟发出一次
//    public void processQueueOrder() {
//        log.info("处理排队调度:{}", LocalDateTime.now());
//        Integer queue = orderMapper.countStatus(Orders.PENDING_QUEUE);
//        //发送通知到C端用户-4 代表队列
//        Map map = new HashMap();
//        map.put("type", 4);
//        map.put("num", queue);
//        webSocketServer.sendToAllClient(JSON.toJSONString(map));
//    }

    /**
     * 未支付 -> 取消
     * 已支付 -> 完成
     * 退款   -> 取消
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点出发一次
    public void processDeliveryOrder() {
        log.info("无效订单处理:{}", LocalDateTime.now());
        LocalDateTime endDateTime = LocalDateTime.now().plusMinutes(-60);
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(-24);
        List<Orders> ordersList = orderMapper.getOrdersByTimeLT(startDateTime, endDateTime);
        if (ordersList != null && ordersList.size() > 0) {
            for (Orders orders : ordersList) {
                Integer payStatus = orders.getPayStatus();
                //支付状态 0未支付 1已支付 2退款
                if (payStatus == Orders.UN_PAID || payStatus == Orders.REFUND) {
                    orders.setStatus(Orders.CANCELLED);
                } else if (payStatus == Orders.PAID) {
                    orders.setStatus(Orders.COMPLETED);
                }
                orderMapper.update(orders);
            }
        }
    }
}
