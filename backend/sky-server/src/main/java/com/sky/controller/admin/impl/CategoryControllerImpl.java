package com.sky.controller.admin.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.CategoryController;
import com.sky.dto.admin.CategoryDTO;
import com.sky.service.admin.CategoryService;
import com.sky.vo.admin.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;


@RestController
@RequestMapping("/admin")
@Tag(name="分类管理")
public class CategoryControllerImpl implements CategoryController {


  @Autowired
  private CategoryService categoryService;



  /**
   * 分页查询
   */
  @Override
  @GetMapping("/category/list")
  @Operation(summary="分页查询")
  public Result<Page<CategoryVO>> selectPage(
          @RequestParam(required = false) String categoryName,
          @RequestParam(required = false) Integer categoryType,
          @RequestParam Integer currentPage,
          @RequestParam Integer pageSize
  ){

    return new Result<>(
            200,
            "分页查询成功",
            categoryService.selectPage(
                    categoryName,
                    categoryType,
                    currentPage,
                    pageSize
            )
    );

  }



  /**
   * 添加分类
   */
  @Override
  @PostMapping("/category/add")
  @Operation(summary="添加分类")
  public Result<String> categoryAdd(
          @RequestBody CategoryDTO categoryDTO
  ){

    categoryService.categoryAdd(categoryDTO);


    return new Result<>(
            200,
            "添加成功",
            null
    );

  }




  /**
   * 修改分类
   */
  @Override
  @PutMapping("/category/update")
  @Operation(summary="修改分类")
  public Result<String> categoryUpdate(
          @RequestBody CategoryDTO categoryDTO
  ){

    categoryService.categoryUpdate(categoryDTO);


    return new Result<>(
            200,
            "修改成功",
            null
    );

  }




  /**
   * 删除分类
   */
  @Override
  @DeleteMapping("/category/{id}")
  @Operation(summary="删除分类")
  public Result<String> categoryDelete(
          @PathVariable Integer id
  ){

    categoryService.categoryDelete(id);


    return new Result<>(
            200,
            "删除成功",
            null
    );

  }




  /**
   * 查询所有分类
   */
  @Override
  @GetMapping("/category/all")
  @Operation(summary="查询所有分类")
  public Result<List<CategoryVO>> getCategoryList(){

    return new Result<>(
            200,
            "获取成功",
            categoryService.getCategoryList()
    );

  }


}