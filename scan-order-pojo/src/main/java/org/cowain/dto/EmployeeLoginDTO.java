package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工登录请求参数")
public class EmployeeLoginDTO implements Serializable {

    @ApiModelProperty(value = "账号",required = true)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
