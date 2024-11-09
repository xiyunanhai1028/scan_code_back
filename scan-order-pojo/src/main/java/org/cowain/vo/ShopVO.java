package org.cowain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@ApiModel("商铺信息返回数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopVO implements Serializable {

    private static final long serialVersionUID = 8118970838470938843L;

    @ApiModelProperty("商铺ID")
    private Long id;

    @ApiModelProperty("商铺名称")
    private String name;

    @ApiModelProperty("商铺状态")
    private int status;

    @ApiModelProperty("商铺图片")
    private String image;

    @ApiModelProperty("商铺地址")
    private String address;

    @ApiModelProperty("营业开始时间")
    private LocalTime beginTime;

    @ApiModelProperty("营业结束时间")
    private LocalTime endTime;

    @ApiModelProperty("商铺简介")
    private String description;
}
