package org.cowain.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cowain.result.Result;
import org.cowain.service.ReportService;
import org.cowain.vo.OrderReportVO;
import org.cowain.vo.SalesTop10ReportVO;
import org.cowain.vo.TurnoverReportVO;
import org.cowain.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Api(tags = "统计相关接口")
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;

    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(Integer type) {
        log.info("营业额统计:{}", type);
        TurnoverReportVO turnoverReportVO = reportService.getTurnoverStatistics(type);
        return Result.success(turnoverReportVO);
    }

    @ApiOperation("用户统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(Integer type) {
        log.info("用户统计:{}", type);
        UserReportVO userStatistics = reportService.getUserStatistics(type);
        return Result.success(userStatistics);
    }

    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistics(Integer type) {
        log.info("订单统计:{}", type);
        return Result.success(reportService.getOrderStatistics(type));
    }

    @ApiOperation("销量排名top10")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(Integer type) {
        return Result.success(reportService.getSalesTop10(type));
    }
}
