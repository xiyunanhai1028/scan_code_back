package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cowain.entity.SetmealDish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel("保存套餐参数")
@Data
public class SetmealDTO implements Serializable {
    private static final long serialVersionUID = -6850182370749836972L;

    @ApiModelProperty(value = "套餐ID")
    private Long id;

    @ApiModelProperty(value = "分类ID",required = true)
    private Long categoryId;

    @ApiModelProperty(value = "套餐名称",required = true)
    private String name;

    @ApiModelProperty(value = "套餐价格",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "套餐状态",required = true)
    private Integer status;

    @ApiModelProperty(value = "套餐描述")
    private String description;

    @ApiModelProperty(value = "套餐菜品关系",required = true)
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    @ApiModelProperty(value = "套餐图片",required = true)
    private String image;
}
