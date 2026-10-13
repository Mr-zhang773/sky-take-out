package com.sky.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.DishDTO;
import com.sky.vo.admin.DishVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

import java.util.Map;


public interface DishController {


    /**
     * 分页查询菜品
     */
    Result<Page<DishVO>> selectPage(
            @RequestParam(required = false) String dishName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam Integer currentPage,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer status
    );


    /**
     * 添加菜品
     */
    Result<String> dishAdd(
            @RequestBody DishDTO dishDTO
    );


    /**
     * 修改菜品
     */
    Result<String> dishUpdate(
            @RequestBody DishDTO dishDTO
    );


    /**
     * 删除菜品
     */
    Result<String> dishDelete(
            @PathVariable Integer id
    );


    /**
     * 修改菜品状态
     */
    Result<String> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String,Integer> params
    );

}