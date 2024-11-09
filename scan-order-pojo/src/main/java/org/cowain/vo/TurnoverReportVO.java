package org.cowain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoverReportVO implements Serializable {
    private static final long serialVersionUID = -5273489339611234426L;
    //日期，以逗号隔开
    private String dateList;
    //营业额,以逗号隔开
    private String turnoverList;
}
