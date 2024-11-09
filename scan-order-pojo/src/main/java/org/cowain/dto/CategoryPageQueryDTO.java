package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("分类列表请求参数")
public class CategoryPageQueryDTO implements Serializable {
    private static final long serialVersionUID = -4562570468567848831L;

    @ApiModelProperty(value = "当前页",required = true)
    private int page;

    @ApiModelProperty(value = "每页多少条",required = true)
    private int pageSize;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类类型")
    private int type;
}
