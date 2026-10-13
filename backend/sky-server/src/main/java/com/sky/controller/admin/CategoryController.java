package com.sky.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.CategoryDTO;
import com.sky.vo.admin.CategoryVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

import java.util.List;


public interface CategoryController {


    /**
     * 分页查询分类
     */
    Result<Page<CategoryVO>> selectPage(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer categoryType,
            @RequestParam Integer currentPage,
            @RequestParam Integer pageSize
    );



    /**
     * 添加分类
     */
    Result<String> categoryAdd(
            @RequestBody CategoryDTO categoryDTO
    );



    /**
     * 修改分类
     */
    Result<String> categoryUpdate(
            @RequestBody CategoryDTO categoryDTO
    );



    /**
     * 删除分类
     */
    Result<String> categoryDelete(
            @PathVariable Integer id
    );



    /**
     * 查询全部分类
     */
    Result<List<CategoryVO>> getCategoryList();

}