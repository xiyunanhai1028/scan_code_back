package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("新增员工参数")
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 5389668740015248398L;

    @ApiModelProperty(value = "员工ID")
    private Long id;

    @ApiModelProperty(value = "角色")
    private Integer role;

    @ApiModelProperty(value = "员工账号", required = true)
    private String username;

    @ApiModelProperty(value = "员工姓名", required = true)
    private String name;

    @ApiModelProperty(value = "员工手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "员工性别", required = true)
    private String sex;

    @ApiModelProperty(value = "员工身份证号", required = true)
    private String idNumber;
}
