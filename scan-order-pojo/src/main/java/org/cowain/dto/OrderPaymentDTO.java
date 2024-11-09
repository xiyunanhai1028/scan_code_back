package org.cowain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderPaymentDTO implements Serializable {
    private static final long serialVersionUID = -1778046929167041839L;
    //订单号
    private String orderNumber;
    //付款方式
    private Integer payMethod;
    //支付金额
    private BigDecimal amount;
}
