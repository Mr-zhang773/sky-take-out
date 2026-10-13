package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.DishController;
import com.sky.dto.admin.DishDTO;
import com.sky.vo.admin.DishVO;
import com.sky.service.admin.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@Tag(name="菜品管理")
public class DishControllerImpl implements DishController {


    @Autowired
    private DishService dishService;


    /**
     * 分页查询菜品
     */
    @Override
    @GetMapping("/dish/list")
    @Operation(summary="分页查询")
    public Result<Page<DishVO>> selectPage(
            @RequestParam(required = false) String dishName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam Integer currentPage,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer status
    ){

        return new Result<>(
                200,
                "分页查询成功",
                dishService.selectPage(
                        dishName,
                        categoryId,
                        currentPage,
                        pageSize,
                        status
                )
        );
    }



    /**
     * 添加菜品
     */
    @Override
    @PostMapping("/dish/add")
    @Operation(summary="添加菜品")
    public Result<String> dishAdd(
            @RequestBody DishDTO dishDTO
    ){

        dishService.dishAdd(dishDTO);

        return new Result<>(
                200,
                "添加成功",
                null
        );
    }



    /**
     * 修改菜品
     */
    @Override
    @PutMapping("/dish/update")
    @Operation(summary="修改菜品")
    public Result<String> dishUpdate(
            @RequestBody DishDTO dishDTO
    ){

        dishService.dishUpdate(dishDTO);

        return new Result<>(
                200,
                "修改成功",
                null
        );
    }



    /**
     * 删除菜品
     */
    @Override
    @DeleteMapping("/dish/{id}")
    @Operation(summary="删除菜品")
    public Result<String> dishDelete(
            @PathVariable Integer id
    ){

        dishService.dishDelete(id);

        return new Result<>(
                200,
                "删除成功",
                null
        );
    }



    /**
     * 修改菜品状态
     */
    @Override
    @PutMapping("/dish/{id}/status")
    @Operation(summary="修改菜品状态")
    public Result<String> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String,Integer> params
    ){

        Integer status = params.get("status");

        dishService.updateStatus(id,status);

        return new Result<>(
                200,
                "状态修改成功",
                null
        );
    }
    }

