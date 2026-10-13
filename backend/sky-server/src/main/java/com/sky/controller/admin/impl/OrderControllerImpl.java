package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.OrderController;
import com.sky.service.admin.OrderService;
import com.sky.entity.Order;
import com.sky.vo.admin.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.Map;

@RestController
@RequestMapping("/admin")

public class OrderControllerImpl implements OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping("/order/list")
    public Result<Page<OrderVO>> dishList(@RequestParam (defaultValue = "1") Integer page, @RequestParam (defaultValue = "10")Integer pageSize, @RequestParam (required = false) String status){
        return new Result<>(200,"订单加载成功",orderService.dishList(page,pageSize,status));
    }

    @Override
    @PutMapping("/order/status/{id}")
    public Result<String> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String,String> params
    ){

        String status = params.get("status");

        orderService.updateStatus(id,status);

        return new Result<>(
                200,
                "订单状态修改成功",
                null
        );
    }
}

