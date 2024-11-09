package org.cowain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 5303082215556857434L;
    /**
     *
     * 订单状态 1排队 2做餐 3出餐 4完成 5取消 6已退款
     */
//    public static final Integer PENDING_PAYMENT = 1;
//    public static final Integer TO_BE_CONFIRMED = 2;
//    public static final Integer CONFIRMED = 3;
//    public static final Integer DELIVERY_IN_PROGRESS = 4;
//    public static final Integer COMPLETED = 5;
//    public static final Integer CANCELLED = 6;
    public static final Integer PENDING_QUEUE = 1;//排队
    public static final Integer MAKING_MEAL = 2;//做餐
    public static final Integer DINING_OUT = 3;//出餐
    public static final Integer COMPLETED = 4;//已完成
    public static final Integer CANCELLED = 5;//已取消
    public static final Integer REFUNDED = 6;//已退款
//    public static final Integer COMMENTED = 7;//已评论

    /**
     * 支付状态 0未支付 1已支付 2退款
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;


    private Long id;

    //订单号
    private String number;

    private Integer status;

    //下单用户id
    private Long userId;
    private String userIds;

    //下单时间
    private LocalDateTime orderTime;

    //结账时间
    private LocalDateTime checkoutTime;

    //支付方式 1微信，2支付宝
    private Integer payMethod;

    //支付状态 0未支付 1已支付 2退款
    private Integer payStatus;

    //实收金额
    private BigDecimal amount;

    //备注
    private String remark;

    //用户名
    private String userName;

    //手机号
    private String phone;


    //订单取消原因
    private String cancelReason;

    //订单拒绝原因
    private String rejectionReason;

    //订单取消时间
    private LocalDateTime cancelTime;


    //桌位号
    private Integer sitNum;

    //是否评论
    private Integer isComment;
}
