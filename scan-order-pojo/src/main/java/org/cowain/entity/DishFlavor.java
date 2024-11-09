package org.cowain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = -2587390099533868942L;
    private Long id;
    private Long dishId;
    private String name;
    private String value;
}
