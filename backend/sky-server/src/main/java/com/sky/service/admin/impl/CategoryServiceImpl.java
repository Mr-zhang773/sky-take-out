package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.mapper.CategoryMapper;
import com.sky.service.admin.CategoryService;
import com.sky.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public  Page<Category> selectPage(String categoryName,Integer categoryType,Integer currentPage, Integer pageSize) {
        Page<Category> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getId);  // 按 id 升序
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            wrapper.like(Category::getCategoryName, categoryName);
        }
        // 分类类型精确查询
        if (categoryType != null) {
            wrapper.eq(Category::getCategoryType, categoryType);
        }
        return categoryMapper.selectPage(page, wrapper);
    }

    @Override
    public Integer categoryAdd(Category category){
       return categoryMapper.insert(category);
    }

    @Override
    public  Integer categoryUpdate(Category category){

        return categoryMapper.updateById(category);
    }

    @Override
    public Integer categoryDelete(Integer id){
        return categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> getCategoryList(){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getId);
        return categoryMapper.selectList(wrapper);
    }

}