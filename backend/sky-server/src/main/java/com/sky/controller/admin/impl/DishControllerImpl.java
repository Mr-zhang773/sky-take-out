package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.DishController;
import com.sky.service.admin.DishService;
import entity.Dish;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

@RestController
@RequestMapping("/api")
@Tag(name="菜品管理")
public class DishControllerImpl implements DishController {
    @Autowired
    private DishService dishService;

    @Override
    @GetMapping("/dish/list")
    @Operation(summary="分页查询")
    public Result<Page<Dish>> selectPage(@RequestParam(required = false) String dishName, @RequestParam(required = false) Integer categoryId, @RequestParam Integer currentPage, @RequestParam  Integer pageSize,@RequestParam(required = false) Integer status) {
        return new Result<>(200, "分页查询成功", dishService.selectPage(dishName, categoryId, currentPage, pageSize, status));
    }
        @Override
        @PostMapping("/dish/add")
        @Operation(summary = "添加菜品")
        public Result<Integer> dishAdd(@RequestBody Dish dish){
            return new Result<>(200,"添加成功",dishService.dishAdd(dish));
        }

    @Override
    @PutMapping ("dish/update")
    @Operation(summary = "修改菜品")
    public Result<Integer> dishUpdate(@RequestBody Dish dish){
        return new Result<>(200,"修改成功",dishService.dishUpdate(dish));
    }

    @Override
    @DeleteMapping ("/dish/{id}")
    @Operation(summary = "删除菜品")
    public Result<Integer> dishDelete(@PathVariable Integer id){
        return new Result<>(200,"删除成功",dishService.dishDelete(id));
    }
    }

