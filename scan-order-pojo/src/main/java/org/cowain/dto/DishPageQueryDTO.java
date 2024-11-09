package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("分页查询菜品列表参数")
@Data
public class DishPageQueryDTO implements Serializable {

    private static final long serialVersionUID = -1645242250112583656L;
    @ApiModelProperty(value = "当前页", required = true)
    private Integer page;
    @ApiModelProperty(value = "每页多少条数据", required = true)
    private Integer pageSize;
    @ApiModelProperty(value = "菜品名称")
    private String name;
    @ApiModelProperty(value = "菜品分类ID")
    private Long categoryId;
    @ApiModelProperty("状态")
    private Integer status;
}
