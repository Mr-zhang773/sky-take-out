package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOrderMapper extends BaseMapper<Order> {
    // 基础 CRUD 方法已被 BaseMapper 继承
    // 以后如果有复杂的联表查询，在这里写自定义方法
}