package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.DishDTO;
import com.sky.vo.admin.DishVO;

public interface DishService {


    /**
     * 分页查询菜品
     */
    Page<DishVO> selectPage(
            String dishName,
            Integer categoryId,
            Integer currentPage,
            Integer pageSize,
            Integer status
    );


    /**
     * 添加菜品
     */
    void dishAdd(DishDTO dishDTO);


    /**
     * 修改菜品
     */
    void dishUpdate(DishDTO dishDTO);


    /**
     * 删除菜品
     */
    void dishDelete(Integer id);


    /**
     * 修改菜品状态
     */
    void updateStatus(
            Integer id,
            Integer status
    );

}