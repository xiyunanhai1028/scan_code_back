package org.cowain.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1706403180956652285L;

    //主键
    private Long id;

    //套餐ID
    private Long setmealId;

    //菜品id
    private Long dishId;

    //菜品名称
    private String name;

    //菜品原价
    private BigDecimal price;

    //份数
    private Integer copies;
}
