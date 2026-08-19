package com.sky.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import entity.Category;
import entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

import java.util.List;

public interface CategoryController {
    Result<Page<Category>> selectPage(@RequestParam String categoryName, @RequestParam Integer categoryType, @RequestParam Integer currentPage, @RequestParam Integer pageSize);

    Result<Integer> categoryAdd(@RequestBody Category category);

    Result<Integer> categoryUpdate(@RequestBody Category category);

    Result<Integer> categoryDelete(@PathVariable Integer id);
    Result<List<Category>> getCategoryList();
}