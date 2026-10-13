package com.sky.service.user.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.config.WeChatConfig;
import com.sky.entity.WechatUser;
import com.sky.mapper.WechatUserMapper;
import com.sky.service.user.UserService;
import com.sky.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private WeChatConfig weChatConfig;


    @Autowired
    private WechatUserMapper wechatUserMapper;


    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public String wechatLogin(String code) {


        /**
         * 1. 根据微信code换取openid和session_key
         */
        String url =
                "https://api.weixin.qq.com/sns/jscode2session"
                        + "?appid=" + weChatConfig.getAppid()
                        + "&secret=" + weChatConfig.getSecret()
                        + "&js_code=" + code
                        + "&grant_type=authorization_code";


        RestTemplate restTemplate = new RestTemplate();


        String result =
                restTemplate.getForObject(url, String.class);



        JSONObject jsonObject =
                JSONObject.parseObject(result);



        String openid =
                jsonObject.getString("openid");


        String sessionKey =
                jsonObject.getString("session_key");



        if(openid == null){

            throw new RuntimeException("微信登录失败");

        }



        /**
         * 2. 根据openid查询用户
         */
        LambdaQueryWrapper<WechatUser> wrapper =
                new LambdaQueryWrapper<>();


        wrapper.eq(
                WechatUser::getOpenid,
                openid
        );


        WechatUser user =
                wechatUserMapper.selectOne(wrapper);



        /**
         * 3. 第一次登录，创建用户
         */
        if(user == null){


            user = new WechatUser();


            user.setOpenid(openid);


            user.setSessionKey(sessionKey);


            wechatUserMapper.insert(user);



        }else{


            /**
             * 已存在用户
             * 更新新的session_key
             */
            user.setSessionKey(sessionKey);


            wechatUserMapper.updateById(user);

        }



        /**
         * 4. 生成JWT
         *
         * 注意：
         * 这里一定放数据库用户id
         * 后面订单通过JWT获取userId
         */
        Map<String,Object> claims =
                new HashMap<>();

        claims.put(
                "userId",
                user.getId().longValue()
        );



        return jwtUtil.createToken(claims);

    }

}