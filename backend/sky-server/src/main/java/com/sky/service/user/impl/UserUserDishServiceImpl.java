package com.sky.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.mapper.DishMapper;
import com.sky.service.user.UserDishService;
import entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUserDishServiceImpl implements UserDishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public Page<Dish> getList(Integer categoryId, Integer page, Integer size) {
        Page<Dish> pageInfo = new Page<>(page, size);
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(Dish::getCategoryId, categoryId);
        }
        wrapper.eq(Dish::getStatus, 1);
        wrapper.orderByAsc(Dish::getId);
        return dishMapper.selectPage(pageInfo, wrapper);
    }
}