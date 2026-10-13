 package com.sky.vo.admin;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商家端订单菜品详情视图对象
 */
@Data
public class OrderDetailVO {

    /** 订单详情ID */
    private Long id;

    /** 菜品ID */
    private Long dishId;

    /** 菜品名称 */
    private String dishName;

    /**
     * 下单时菜品单价
     */
    private BigDecimal dishPrice;

    /** 购买数量 */
    private Integer quantity;
}
