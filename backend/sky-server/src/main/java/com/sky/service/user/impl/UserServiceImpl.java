package com.sky.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.config.WeChatConfig;
import com.sky.mapper.WechatUserMapper;
import com.sky.service.user.UserService;
import com.sky.utils.JwtUtil;
import entity.WechatUser;
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
    private JwtUtil jwtUtil;

    @Autowired
    private WechatUserMapper wechatUserMapper;  // ← 注入 Mapper

    @Override
    public String wechatLogin(String code) {
        // 1. 调用微信接口，用 code 换取 openid
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + weChatConfig.getAppid() +
                "&secret=" + weChatConfig.getSecret() +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // 2. 解析返回结果
        JSONObject jsonObject = JSONObject.parseObject(response);
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");

        if (openid == null) {
            String errMsg = jsonObject.getString("errmsg");
            throw new RuntimeException("微信登录失败：" + errMsg);
        }

        System.out.println("openid：" + openid);

        // 3. 根据 openid 查询用户，不存在则创建
        LambdaQueryWrapper<WechatUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatUser::getOpenid, openid);
        WechatUser user = wechatUserMapper.selectOne(wrapper);

        if (user == null) {
            user = new WechatUser();
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            wechatUserMapper.insert(user);
        }

        // 4. 生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("openid", openid);

        String token = jwtUtil.createToken(claims);

        return token;
    }
}