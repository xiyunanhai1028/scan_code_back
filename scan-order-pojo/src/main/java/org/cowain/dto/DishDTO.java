package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cowain.entity.DishFlavor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel("保存/修改菜品相关参数")
@Data
public class DishDTO implements Serializable {
    private static final long serialVersionUID = 2317092867763561668L;
    @ApiModelProperty("菜品ID")
    private Long id;

    @ApiModelProperty(value = "菜品名称", required = true)
    private String name;

    @ApiModelProperty(value = "菜品分类ID", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "菜品价格", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "菜品图片")
    private String image;

    @ApiModelProperty(value = "菜品描述")
    private String description;

    @ApiModelProperty(value = "菜品状态：0：停售，1：启售", required = true)
    private Integer status;

    @ApiModelProperty(value = "菜品口味")
    private List<DishFlavor> flavors = new ArrayList<>();


}
