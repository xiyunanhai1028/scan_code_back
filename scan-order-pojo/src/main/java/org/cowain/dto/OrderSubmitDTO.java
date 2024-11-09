package org.cowain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitDTO implements Serializable {
    private static final long serialVersionUID = -2529194478537683762L;
    //付款方式
    private int payMethod;
    //备注
    private String remark;
    //总金额
    private BigDecimal amount;
    //桌位号
    private int sitNum;

}
