package com.sky.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.Order;

public interface UserOrderService extends IService<Order> {
    // 未来如果有特殊的订单业务方法，在这里定义
    // 比如：void cancelOrder(Long orderId);
    // 比如：List<Order> getMyOrders(Integer userId);
}