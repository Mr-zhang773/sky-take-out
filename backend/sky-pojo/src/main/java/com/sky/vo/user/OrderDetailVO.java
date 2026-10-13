package com.sky.vo.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailVO {
    private String dishName;
    private BigDecimal dishPrice;
    private Integer quantity;
    private String dishImage;

}
