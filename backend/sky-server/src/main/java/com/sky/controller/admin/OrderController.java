package com.sky.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Order;
import com.sky.vo.admin.OrderVO;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

import java.util.Map;

public interface OrderController {
    Result<Page<OrderVO>> dishList(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam String status);
    Result<String> updateStatus(Long id, Map<String,String> params);
}
