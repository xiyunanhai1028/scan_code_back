package org.cowain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cowain.entity.SetmealDish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "分页套餐返回数据")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetmealVO implements Serializable {

    private static final long serialVersionUID = -7517993326298280513L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    //
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    //
    @ApiModelProperty(value = "套餐名称")
    private String name;

    //
    @ApiModelProperty(value = "套餐价格")
    private BigDecimal price;

    //
    @ApiModelProperty(value = "状态 0:停用 1:启用")
    private Integer status;

    //
    @ApiModelProperty(value = "描述信息")
    private String description;

    //
    @ApiModelProperty(value = "图片")
    private String image;

    //
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    //
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    //
    @ApiModelProperty(value = "套餐和菜品的关联关系")
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
