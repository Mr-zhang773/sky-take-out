package com.sky.interceptor;


import com.sky.context.BaseContext;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class JwtInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        System.out.println("进入拦截器：" + request.getRequestURI());


        // 获取token
        String token = request.getHeader("Authorization");


        System.out.println("请求token:" + token);



        // 没有token
        if(token == null || token.isEmpty()){

            response.setStatus(401);

            return false;

        }



        // 去掉Bearer
        if(token.startsWith("Bearer ")){

            token = token.substring(7);

        }



        try {


            // 解析JWT
            Claims claims =
                    jwtUtil.parseToken(token);



            // 获取用户id
            Long userId =
                    claims.get(
                            "userId",
                            Long.class
                    );



            if(userId == null){

                response.setStatus(401);

                return false;

            }



            // 保存当前用户
            BaseContext.setCurrentId(userId);



            return true;



        }catch(Exception e){


            e.printStackTrace();


            response.setStatus(401);

            return false;

        }

    }



    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ){

        // 清理ThreadLocal
        BaseContext.remove();

    }

}