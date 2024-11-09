package org.cowain.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Setmeal implements Serializable {
    private static final long serialVersionUID = 2421586897127626171L;

    //主键
    private Long id;

    //分类ID
    private Long categoryId;

    //套餐名称
    private String name;

    //套餐价格
    private BigDecimal price;

    //套餐状态 1：启售，0:停售
    private Integer status;

    //套餐描述
    private String description;

    //套餐图片
    private String image;

    //创建时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

    //创建人
    private Long createUser;

    //更新人
    private Long updateUser;

    private Integer monthSales;
}
