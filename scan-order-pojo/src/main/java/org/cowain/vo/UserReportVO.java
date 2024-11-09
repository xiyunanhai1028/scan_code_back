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
public class UserReportVO implements Serializable {
    private static final long serialVersionUID = 835526065658491165L;
    private String dateList;
    //每天累计用户
    private String totalUserList;
    //每天新增用户
    private String newUserList;
}
