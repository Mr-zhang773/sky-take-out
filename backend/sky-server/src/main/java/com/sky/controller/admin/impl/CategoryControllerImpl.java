package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.CategoryController;
import com.sky.service.admin.CategoryService;
import entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name="分类管理")
public class CategoryControllerImpl implements CategoryController {
  @Autowired
  private CategoryService categoryService;

    @Override
    @GetMapping("/category/list")
    @Operation(summary="分页查询")
    public Result<Page<Category>> selectPage(@RequestParam(required = false) String categoryName,@RequestParam(required = false) Integer categoryType, @RequestParam Integer currentPage, @RequestParam  Integer pageSize){
        return new Result<>(200,"分页查询成功", categoryService.selectPage(categoryName,categoryType,currentPage,pageSize));
    }

    @Override
    @PostMapping("/category/add")
    @Operation(summary = "添加套餐")
  public Result<Integer> categoryAdd(@RequestBody Category category){
      return new Result<>(200,"添加成功",categoryService.categoryAdd(category));
    }

  @Override
  @PutMapping ("/category/update")
  @Operation(summary = "修改套餐")
  public Result<Integer> categoryUpdate(@RequestBody Category category){
    return new Result<>(200,"修改成功",categoryService.categoryUpdate(category));
  }

  @Override
  @DeleteMapping ("/category/{id}")
  @Operation(summary = "删除套餐")
  public Result<Integer> categoryDelete(@PathVariable Integer id){
    return new Result<>(200,"删除成功",categoryService.categoryDelete(id));
  }

  @Override
  @GetMapping("/category/all")
  @Operation(summary="分页查询")
  public Result<List<Category>> getCategoryList(){
    return new Result<>(200,"获取表成功", categoryService.getCategoryList());
  }

}