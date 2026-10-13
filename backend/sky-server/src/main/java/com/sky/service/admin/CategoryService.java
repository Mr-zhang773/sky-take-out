package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Category;

import java.util.List;

public interface CategoryService {
     Page<Category> selectPage(String categoryName,Integer categoryType, Integer currentPage, Integer pageSize);
     Integer categoryAdd(Category category);
     Integer categoryUpdate(Category category);
     Integer categoryDelete(Integer id);
     List<Category> getCategoryList();
}