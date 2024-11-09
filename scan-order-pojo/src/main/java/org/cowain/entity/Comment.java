package org.cowain.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("评论相关接口")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = -6243439393777798725L;

    private Long id;

    private Long userId;

    private Long orderId;

    private String mobile;

    private String dishIds;

    private String setmealIds;

    private String description;

    private String images;

    private String status;

    private LocalDateTime createTime;
}
