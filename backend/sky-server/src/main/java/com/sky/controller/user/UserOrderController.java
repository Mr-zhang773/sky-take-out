package com.sky.controller.user;


import com.sky.dto.user.OrderDto;
import com.sky.service.user.UserOrderService;
import com.sky.vo.user.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;


@RestController
@RequestMapping("/user/order")
public class UserOrderController {


    @Autowired
    private UserOrderService userOrderService;

    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody OrderDto orderDto){
        userOrderService.createOrder(orderDto);
        return new Result<>(
                200,
                "下单成功",
                null
        );
    }

    @GetMapping("/list")
    public Result<List<OrderVO>> getUserOrder(){
        return new Result<>(200,"订单查询成功",userOrderService.getUserOrders());
    }
}