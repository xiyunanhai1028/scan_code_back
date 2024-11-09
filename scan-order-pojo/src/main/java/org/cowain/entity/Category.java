package org.cowain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private static final long serialVersionUID = 4535839188763152591L;

    private Long id;

    //类型：1 菜品，2：套餐
    private Integer type;

    //分类名称
    private String name;

    //顺序
    private Integer sort;

    //0:禁用 1：启用
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
