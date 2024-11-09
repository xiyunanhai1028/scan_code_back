package org.cowain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ApiModel("店铺信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Serializable {
    private static final long serialVersionUID = -4402893609052312822L;
    @ApiModelProperty("店铺ID")
    private Long id;

    @ApiModelProperty(value = "店铺名称", required = true)
    private String name;

    @ApiModelProperty(value = "店铺地址", required = true)
    private String address;

    @ApiModelProperty(value = "店铺状态", required = true)
    private int status;

    @ApiModelProperty(value = "店铺图片", required = true)
    private String image;

    @ApiModelProperty(value = "店铺简介", required = true)
    private String description;

    @ApiModelProperty(value = "营业开始时间", required = true)
    private LocalTime beginTime;

    @ApiModelProperty(value = "营业结束时间", required = true)
    private LocalTime endTime;
}
