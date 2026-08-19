package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Order;

public interface OrderService {

    Page<Order> dishList(Integer page, Integer pageSize, String status);

    void updateStatus(Long id, String status);
}