package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("添加购物车数据")
@Data
public class ShoppingCartDTO implements Serializable {
    @ApiModelProperty("菜品ID")
    private Long dishId;

    @ApiModelProperty("套餐ID")
    private Long setmealId;

    @ApiModelProperty("菜品口味")
    private String dishFlavor;

    @ApiModelProperty("桌位号")
    private int sitNum;
}
