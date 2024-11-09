package org.cowain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {


    private static final long serialVersionUID = -2098224078375184658L;
    private Long orderId;


    private String dishIds;

    private String setmealIds;

    private String description;

    private String images;

    private String status;


}
