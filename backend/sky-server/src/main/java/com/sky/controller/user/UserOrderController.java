package com.sky.controller.user;

import com.sky.service.user.UserOrderService;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;


import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")   // 路径也改成 /user/order，更清晰
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody Order order) {
        // 1. 补默认值
        order.setUserId(1);  // TODO: 等登录做好后，从 token 里取真实 userId
        order.setStatus("待支付");
        order.setCreateTime(LocalDateTime.now().toString());
        order.setUpdateTime(LocalDateTime.now().toString());

        // 2. 保存到数据库
        boolean saved = userOrderService.save(order);

        if (saved) {
            return new Result<>(200,"储存成功",null);
        } else {
            return new Result<>(500,"储存失败",null);
        }
    }
}