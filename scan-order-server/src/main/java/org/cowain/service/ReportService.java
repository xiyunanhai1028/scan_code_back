package org.cowain.service;

import org.cowain.vo.OrderReportVO;
import org.cowain.vo.SalesTop10ReportVO;
import org.cowain.vo.TurnoverReportVO;
import org.cowain.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    //营业额统计
    TurnoverReportVO getTurnoverStatistics(Integer type);

    //用户统计
    UserReportVO getUserStatistics(Integer type);

    //订单统计
    OrderReportVO getOrderStatistics(Integer type);

    //销量排名top10
    SalesTop10ReportVO getSalesTop10(Integer type);
}
