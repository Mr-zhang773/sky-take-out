package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.OrderController;
import com.sky.service.admin.OrderService;
import entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

@RestController
@RequestMapping("api/admin")
@Tag(name="订单信息管理")
public class OrderControllerImpl implements OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/order/list")
    @Operation(summary = "订单列表")
    public Result<Page<Order>> dishList(@RequestParam (defaultValue = "1") Integer page, @RequestParam (defaultValue = "10")Integer pageSize,@RequestParam (required = false) String status){
        return new Result<>(200,"订单加载成功",orderService.dishList(page,pageSize,status));
    }


    @PutMapping("/order/status/{id}")
    @Operation(summary = "更新订单状态")
    public Result<String> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        orderService.updateStatus(id, status);
        return new Result<>(200, "更新成功", null);
    }
}

