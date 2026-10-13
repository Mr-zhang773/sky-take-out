package com.sky.config;


import com.sky.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private JwtInterceptor jwtInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(jwtInterceptor)

                // 拦截所有请求
                .addPathPatterns("/**")


                // 不需要登录的接口
                .excludePathPatterns(

                        // 管理员登录
                        "/admin/**",

                        // 微信用户登录
                        "/api/user/login"

                );

    }

}