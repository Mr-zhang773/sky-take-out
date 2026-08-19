package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.mapper.DishMapper;
import com.sky.service.admin.DishService;
import entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Override
    public Page<Dish> selectPage(String dishName, Integer categoryId, Integer currentPage, Integer pageSize, Integer status) {
        Page<Dish> page = new Page<>(currentPage, pageSize);
        return dishMapper.selectPageWithCategory(page, dishName, categoryId, status);
    }
        @Override
        public Integer dishAdd(Dish dish){
            return dishMapper.insert(dish);
        }

    @Override
    public Integer dishUpdate(Dish dish){
        return dishMapper.updateById(dish);
    }
    @Override
    public Integer dishDelete(Integer id){
        return dishMapper.deleteById(id);
    }
    }


