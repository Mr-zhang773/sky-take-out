package com.sky.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Order;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

public interface OrderController {
    Result<Page<Order>> dishList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String status);
    Result<String> updateStatus(Long id, String status);
}
