package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "套餐分页查询接口参数")
@Data
public class SetmealPageQueryDTO  implements Serializable {
    private static final long serialVersionUID = 4525756689023613807L;

    @ApiModelProperty(value = "当前页码",required = true)
    private Integer page;

    @ApiModelProperty(value = "每页多少条数据",required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "套餐名称")
    private String name;

    @ApiModelProperty(value = "套餐分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "套餐售卖状态")
    private Integer status;
}
