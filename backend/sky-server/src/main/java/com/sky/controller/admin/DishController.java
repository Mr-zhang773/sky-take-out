package com.sky.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Dish;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

public interface DishController {
    Result<Page<Dish>> selectPage(@RequestParam String dishName, @RequestParam Integer categoryId, @RequestParam Integer currentPage, @RequestParam  Integer pageSize,@RequestParam Integer status);
    Result<Integer> dishAdd(@RequestBody Dish dish);
    Result<Integer> dishUpdate(@RequestBody Dish dish);
    Result<Integer> dishDelete(@PathVariable Integer id);
}
