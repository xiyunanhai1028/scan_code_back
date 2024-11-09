package org.cowain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 7636553651461407291L;

    private Long id;

    //用户ID
    private Long userId;

    //收货人
    private String consignee;

    //性别:0 女  1 男
    private String sex;

    //手机号
    private String phone;

    //省编码
    private String provinceCode;

    //省名称
    private String provinceName;

    //市编码
    private String cityCode;

    //市名称
    private String cityName;

    //区编码
    private String districtCode;

    //区名称
    private String districtName;

    //详细地址
    private String detail;

    //标签
    private String label;

    //是否默认地址:0 否  1 是
    private Integer isDefault;

}
