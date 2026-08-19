package com.sky.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
        // 分页查询，连表获取分类名称
        @Select("SELECT d.*, c.category_name AS categoryName " +
                "FROM dish d " +
                "LEFT JOIN category c ON d.category_id = c.id " +
                "WHERE d.dish_name LIKE CONCAT('%', #{dishName}, '%') " +
                "AND (#{categoryId} IS NULL OR d.category_id = #{categoryId}) " +
                "AND (#{status} IS NULL OR d.status = #{status}) " +
                "ORDER BY d.id ASC")
        Page<Dish> selectPageWithCategory(Page<Dish> page,
                                          @Param("dishName") String dishName,
                                          @Param("categoryId") Integer categoryId,
                                          @Param("status") Integer status);
    }

