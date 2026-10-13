package com.sky.controller.user;

import com.sky.LoginDTO;
import com.sky.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.Result;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户端-用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "微信登录")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.wechatLogin(loginDTO.getCode());
        return new Result<>(200, "登录成功", token);
    }
}