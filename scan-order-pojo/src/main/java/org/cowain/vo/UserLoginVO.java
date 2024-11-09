package org.cowain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel("登录返回参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {
    private static final long serialVersionUID = 1782650079865163800L;

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("openId")
    private String openid;
    @ApiModelProperty("token用户唯一标识")
    private String token;
}
