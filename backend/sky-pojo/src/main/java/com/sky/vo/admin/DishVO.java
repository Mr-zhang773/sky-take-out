package com.sky.vo.admin;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DishVO {


    private Long id;

    private Long categoryId;



    /**
     * 菜品名称
     */
    private String dishName;


    /**
     * 菜品图片
     */
    private String dishImage;


    /**
     * 分类名称
     */
    private String categoryName;


    /**
     * 菜品价格
     */
    private BigDecimal dishPrice;


    /**
     * 状态
     */
    private Integer status;


    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}