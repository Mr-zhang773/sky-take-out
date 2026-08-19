package com.sky.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Dish;

public interface UserDishService {
    Page<Dish> getList(Integer categoryId, Integer page, Integer size);
}