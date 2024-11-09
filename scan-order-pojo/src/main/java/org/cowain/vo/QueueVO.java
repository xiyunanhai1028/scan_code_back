package org.cowain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel("排队信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueVO implements Serializable {
    private static final long serialVersionUID = 6036455611187098793L;

    @ApiModelProperty("排队人数")
    private Integer queueNum;

    @ApiModelProperty("订单状态")
    private Integer status;
}
