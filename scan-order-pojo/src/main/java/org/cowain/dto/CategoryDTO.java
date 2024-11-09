package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("新增分类请求数据")
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = -3021178478177345314L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty(value = "类型 1 菜品分类 2 套餐分类", required = true)
    private Integer type;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

}
