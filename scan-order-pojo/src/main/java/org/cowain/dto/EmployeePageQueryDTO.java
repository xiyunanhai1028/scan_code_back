package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("员工列表请求参数")
public class EmployeePageQueryDTO implements Serializable {
    private static final long serialVersionUID = 8418943350828816318L;

    @ApiModelProperty(value = "员工姓名", required = false)
    private String name;

    @ApiModelProperty(value = "当前页码", required = true)
    private int page;

    @ApiModelProperty(value = "每页显示记录数", required = true)
    private int pageSize;
}
