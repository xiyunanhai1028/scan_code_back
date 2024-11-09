package org.cowain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.cowain.entity.DishFlavor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel("菜品列表数据")
@Data
public class DishVO implements Serializable {
    private static final long serialVersionUID = 539451729508163395L;
    @ApiModelProperty("菜品ID")
    private Long id;
    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品分类id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("0 停售 1 起售")
    private Integer status;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("菜品关联的口味")
    private List<DishFlavor> flavors = new ArrayList<>();

    @ApiModelProperty("月销量")
    private Integer monthSales;
}
