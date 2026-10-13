package com.sky.service.user;

import com.sky.dto.user.OrderDto;
import com.sky.vo.user.OrderVO;

import java.util.List;

public interface UserOrderService {

    void createOrder(OrderDto orderDto);

    List<OrderVO> getUserOrders();

}