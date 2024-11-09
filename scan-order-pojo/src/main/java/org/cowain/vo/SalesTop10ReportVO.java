package org.cowain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTop10ReportVO implements Serializable {
    private static final long serialVersionUID = -5905054718910036024L;
    //x轴 商品名称
    private String nameList;
    //销量列表
    private String numberList;
}
