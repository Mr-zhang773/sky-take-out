package com.sky.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.service.user.UserDishService;
import entity.Dish;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

@RestController
@RequestMapping("/user/dish")
@Tag(name = "用户端-菜品管理")
public class UserDishController {

    @Autowired
    private UserDishService dishService;

    @GetMapping("/list")
    @Operation(summary = "获取菜品列表")
    public Result<Page<Dish>> list(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "100") Integer size) {
        Page<Dish> pageData = dishService.getList(categoryId, page, size);
        return new Result<>(200, "查询成功", pageData);
    }
}