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
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 5742809928307376891L;
    private Long id;
    //名称
    private String name;
    //图片
    private String image;
    //菜品ID
    private Long dishId;
    //套餐ID
    private Long setmealId;
    //口味
    private String dishFlavor;
    //数量
    private Integer number;
    //价格
    private BigDecimal amount;
    //桌位号
    private Integer sitNum;
    //创建时间
    private LocalDateTime createTime;
    //点餐的用户
    private Long userId;
}
