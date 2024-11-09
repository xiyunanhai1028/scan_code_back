package org.cowain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cowain.dto.GoodsSalesDTO;
import org.cowain.entity.Orders;
import org.cowain.mapper.OrderMapper;
import org.cowain.mapper.UserMapper;
import org.cowain.service.ReportService;
import org.cowain.vo.OrderReportVO;
import org.cowain.vo.SalesTop10ReportVO;
import org.cowain.vo.TurnoverReportVO;
import org.cowain.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    //营业额统计
    public TurnoverReportVO getTurnoverStatistics(Integer type) {
        List<LocalDate> dateList = getDateList2(type);

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //构建查询参数
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);//营业额状态需要是已完成的才算
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0 : turnover;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ','))
                .turnoverList(StringUtils.join(turnoverList, ','))
                .build();
    }

    //用户统计
    public UserReportVO getUserStatistics(Integer type) {
        List<LocalDate> dateList = getDateList2(type);

        //存放每天的新增用户
        List<Integer> newUserList = new ArrayList<>();
        //存放每天的总用户量
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("end", endTime);
            //总用户数据
            Integer totalUser = userMapper.countByMap(map);
            totalUserList.add(totalUser);
            map.put("begin", beginTime);
            Integer newUser = userMapper.countByMap(map);
            newUserList.add(newUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ','))
                .newUserList(StringUtils.join(newUserList, ','))
                .totalUserList(StringUtils.join(totalUserList, ','))
                .build();
    }

    //订单统计
    public OrderReportVO getOrderStatistics(Integer type) {
        List<LocalDate> dateList = getDateList2(type);

        //存放每天的订单数
        List<Integer> orderCountList = new ArrayList<>();
        //存放每天的有效订单
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //查询每天的订单总数
            // select count(id) from orders where order_time > ? and order_time < ?
            Integer orderCount = getOrderCount(beginTime, endTime, null);
            orderCountList.add(orderCount);
            //查询每天的有效订单数
            // select count(id) from orders where order_time > ? and order_time < ? and status = ?
            Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);
            validOrderCountList.add(validOrderCount);
        }

        //计算时间区间内的订单总数量
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        //计算时间区间内的有效订单总数数量
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        //就按订单完成率
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ','))
                .orderCountList(StringUtils.join(orderCountList, ','))//每日订单数
                .validOrderCountList(StringUtils.join(validOrderCountList, ','))//每日有效订单
                .totalOrderCount(totalOrderCount)//订单总数
                .validOrderCount(validOrderCount)//有效订单总数
                .orderCompletionRate(orderCompletionRate)//订单完成率
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(Integer type) {
        LocalDate begin =LocalDate.now().plusWeeks(-1);
        LocalDate end = LocalDate.now().plusDays(-1);
        if(type==0){
            begin = LocalDate.now().plusWeeks(-1);
        }else if(type==1){
            begin = LocalDate.now().plusMonths(-1);
        }else if(type==2){
            begin = LocalDate.now().plusMonths(-3);
        }
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(beginTime, endTime);
        List<String> nameList = salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        return SalesTop10ReportVO
                .builder()
                .nameList(StringUtils.join(nameList, ','))
                .numberList(StringUtils.join(numberList, ','))
                .build();
    }

    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return orderMapper.countByMap(map);
    }

    //获取X周日期
    private List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        //时间 x轴数据
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }

    private List<LocalDate> getDateList2(Integer type) {
        //时间 x轴数据
        List<LocalDate> dateList = new ArrayList<>();

        LocalDate begin = LocalDate.now().plusWeeks(-1);
        if (type == 0) {//本周
            begin = LocalDate.now().plusWeeks(-1);
        } else if (type == 1) {//本月
            begin = LocalDate.now().plusMonths(-1);
        } else if (type == 2) {//3个月
            begin = LocalDate.now().plusMonths(-3);
        }
        LocalDate end = LocalDate.now().plusDays(-1);
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }
}
