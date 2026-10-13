package com.sky.dto.admin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishDTO {

    /**
     * 菜品id
     * 修改时使用
     */
    private Long id;


    /**
     * 菜品名称
     */
    private String dishName;


    /**
     * 菜品图片
     */
    private String dishImage;


    /**
     * 菜品分类id
     */
    private Long categoryId;


    /**
     * 菜品价格
     */
    private BigDecimal dishPrice;


    /**
     * 菜品状态
     * 1 起售
     * 0 停售
     */
    private Integer status;

}