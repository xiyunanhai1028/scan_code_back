package org.cowain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("登录入参")
@Data
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = -3777516385266845022L;
    @ApiModelProperty(value = "微信code", required = true)
    private String code;
    @ApiModelProperty(value = "平台参数",required = true)
    private String provider;
}
