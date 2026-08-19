package com.sky.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.mapper.OrderMapper;
import com.sky.service.user.UserOrderService;
import entity.Order;
import org.springframework.stereotype.Service;

@Service
public class UserOrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements UserOrderService {
    // 目前是空的，因为基础的 save、getById、list 等方法已经被 IService 继承了
    // 以后写复杂业务逻辑（比如“下单扣库存”），就在这里加方法
}