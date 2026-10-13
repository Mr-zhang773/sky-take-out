package com.sky.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.mapper.CategoryMapper;
import com.sky.service.user.UserCategoryService;
import com.sky.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, 1);
        wrapper.orderByAsc(Category::getId);
        return categoryMapper.selectList(wrapper);
    }
}