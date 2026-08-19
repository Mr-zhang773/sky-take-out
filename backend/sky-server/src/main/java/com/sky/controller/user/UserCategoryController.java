package com.sky.controller.user;

import com.sky.service.user.UserCategoryService;
import entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@Tag(name = "用户端-分类管理")
public class UserCategoryController {

    @Autowired
    private UserCategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "获取所有分类")
    public Result<List<Category>> list() {
        List<Category> list = categoryService.getList();
        return new Result<>(200, "查询成功", list);
    }
}