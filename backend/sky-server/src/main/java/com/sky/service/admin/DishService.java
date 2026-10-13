package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Dish;

public interface DishService {
    Page<Dish> selectPage(String dishName, Integer categoryId, Integer currentPage, Integer pageSize,Integer status);
    Integer dishAdd(Dish dish);
    Integer dishUpdate(Dish dish);
    Integer dishDelete(Integer id);
}
