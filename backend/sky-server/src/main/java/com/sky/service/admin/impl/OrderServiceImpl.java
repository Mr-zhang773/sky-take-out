package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.mapper.OrderMapper;
import com.sky.service.admin.OrderService;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Page<Order> dishList(Integer page, Integer pageSize, String status) {
        // 1. 创建分页对象
        Page<Order> pageInfo = new Page<>(page, pageSize);

        // 2. 创建查询条件
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        // 3. 如果状态不为空，按状态筛选
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq(Order::getStatus, status);
        }

        // 4. 按 id 降序（最新订单在前）
        wrapper.orderByDesc(Order::getId);

        // 5. 执行分页查询
        return orderMapper.selectPage(pageInfo, wrapper);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Order order = orderMapper.selectById(id);
        if (order != null) {
            order.setStatus(status);
            orderMapper.updateById(order);
        }
    }
}