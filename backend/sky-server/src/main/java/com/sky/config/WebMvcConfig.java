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
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/admin/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/doc.html",
                        "/admin/register",
                        "/user/login",
                        "/user/category/**",
                        "/user/dish/**",
                        "/category/list",
                        "/dish/list",
                        "/admin/order/**"
                );
    }
}