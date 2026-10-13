package com.sky.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class OrderDetail {


    private Long id;


    private Long orderId;


    private Long dishId;


    private String dishName;


    private BigDecimal dishPrice;


    private Integer quantity;


    private LocalDateTime createTime;

}