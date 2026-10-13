package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Order;
import com.sky.vo.admin.OrderVO;

public interface OrderService {

    Page<OrderVO> dishList(Integer page, Integer pageSize, String status);

    void updateStatus(Long id, String status);
}