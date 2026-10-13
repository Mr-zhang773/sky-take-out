package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.service.admin.OrderService;
import com.sky.entity.Order;
import com.sky.vo.admin.OrderDetailVO;
import com.sky.vo.admin.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Page<OrderVO> dishList(Integer page, Integer pageSize, String status) {
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

        Page<Order> orderPage =
                orderMapper.selectPage(pageInfo, wrapper);
        Page<OrderVO> voPage = new Page<>();

        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());

        List<OrderVO> voList =
                orderPage.getRecords()
                        .stream()
                        .map(order -> {

                            OrderVO vo = new OrderVO();

                            vo.setId(order.getId());
                            vo.setOrderNo(order.getOrderNo());
                            vo.setConsigneeName(order.getConsigneeName());
                            vo.setConsigneePhone(order.getConsigneePhone());
                            vo.setConsigneeAddress(order.getConsigneeAddress());
                            vo.setTotalPrice(order.getTotalPrice());
                            vo.setStatus(order.getStatus());
                            vo.setCreateTime(order.getCreateTime());
                            vo.setRemark(order.getRemark());


                            List<OrderDetail> details =
                                    orderDetailMapper.selectList(
                                            new LambdaQueryWrapper<OrderDetail>()
                                                    .eq(OrderDetail::getOrderId, order.getId())
                                    );


                            List<OrderDetailVO> detailVOList =
                                    details.stream()
                                            .map(detail -> {

                                                OrderDetailVO detailVO =
                                                        new OrderDetailVO();

                                                detailVO.setId(detail.getId());
                                                detailVO.setDishId(detail.getDishId());
                                                detailVO.setDishName(detail.getDishName());
                                                detailVO.setDishPrice(detail.getDishPrice());
                                                detailVO.setQuantity(detail.getQuantity());

                                                return detailVO;

                                            }).toList();


                            vo.setOrderDetails(detailVOList);


                            return vo;

                        }).toList();


        voPage.setRecords(voList);


        return voPage;

    }



    @Override
    public void updateStatus(Long id, String status) {
        Order order = orderMapper.selectById(id);

        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        String currentStatus = order.getStatus();

        boolean validTransition =
                ("待接单".equals(currentStatus)
                        && ("配送中".equals(status) || "已取消".equals(status)))
                        || ("配送中".equals(currentStatus)
                        && "已完成".equals(status));

        if (!validTransition) {
            throw new RuntimeException(
                    "非法的订单状态流转：" + currentStatus + " -> " + status
            );
        }

        int rows = orderMapper.updateStatusIfMatch(
                id, currentStatus, status
        );

        if (rows == 0) {
            throw new RuntimeException("订单状态已变化，请刷新后重试");
        }
    }

}