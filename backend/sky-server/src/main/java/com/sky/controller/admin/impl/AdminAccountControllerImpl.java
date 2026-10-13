package com.sky.controller.admin.impl;

import com.sky.controller.admin.AdminAccountController;
import com.sky.service.admin.AdminAccountService;
import com.sky.entity.Admin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import result.Result;
@RestController
@RequestMapping("/admin")
@Tag(name="商家账号管理")
public class AdminAccountControllerImpl implements AdminAccountController {
    @Autowired
    AdminAccountService adminAccountService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    @PostMapping("/register")
    @Operation(summary = "注册一个商家账号")
    public Result<Integer> adminRegister(@RequestBody Admin admin) {
        return new Result<>(200, "商家账号注册成功", adminAccountService.adminRegister(admin));

    }
    @Override
    @PostMapping("/login")
    @Operation(summary = "登录商家账号")
    public Result<String> adminLogin(@RequestBody Admin admin) {
        return new Result<>(200, "商家账号登录成功", adminAccountService.adminLogin(admin));

    }
    @Override
    @GetMapping("/redis")
    @Operation(summary = "redis测试案例")
    public String testRedis() {
        redisTemplate.opsForValue().set("test", "Hello Redis");
        return "存入成功：" + redisTemplate.opsForValue().get("test");
    }
}
