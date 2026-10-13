package com.sky.dto.user;

import lombok.Data;

@Data
public class OrderDetailDTO {

    // 菜品id
    private Long dishId;

    // 购买数量
    private Integer quantity;

}