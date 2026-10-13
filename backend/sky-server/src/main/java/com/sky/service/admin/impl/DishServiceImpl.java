package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.DishDTO;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import com.sky.service.admin.DishService;
import com.sky.vo.admin.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;



    /**
     * 分页查询菜品
     */
    @Override
    public Page<DishVO> selectPage(
            String dishName,
            Integer categoryId,
            Integer currentPage,
            Integer pageSize,
            Integer status
    ) {


        Page<DishVO> page = new Page<>(
                currentPage,
                pageSize
        );


        return dishMapper.selectPageWithCategory(
                page,
                dishName,
                categoryId,
                status
        );
    }



    /**
     * 添加菜品
     */
    @Override
    public void dishAdd(DishDTO dishDTO) {


        Dish dish = new Dish();


        BeanUtils.copyProperties(
                dishDTO,
                dish
        );


        dishMapper.insert(dish);

    }



    /**
     * 修改菜品
     */
    @Override
    public void dishUpdate(DishDTO dishDTO) {


        Dish dish = new Dish();


        BeanUtils.copyProperties(
                dishDTO,
                dish
        );


        dishMapper.updateById(dish);

    }



    /**
     * 删除菜品
     */
    @Override
    public void dishDelete(Integer id) {

        dishMapper.deleteById(id);

    }



    /**
     * 修改菜品状态
     */
    @Override
    public void updateStatus(
            Integer id,
            Integer status
    ){

        Dish dish = new Dish();

        dish.setId(id);

        dish.setStatus(status);


        dishMapper.updateById(dish);

    }

}