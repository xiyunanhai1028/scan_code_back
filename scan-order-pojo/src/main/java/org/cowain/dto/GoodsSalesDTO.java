package org.cowain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSalesDTO implements Serializable {
    private static final long serialVersionUID = 838351397968726370L;
    //商品名称
    private String name;
    //销量
    private Integer number;
}
