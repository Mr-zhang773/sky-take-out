package com.sky.service.user;

public interface UserService {

    /**
     * 微信登录
     * @param code 微信临时登录凭证
     * @return JWT Token
     */
    String wechatLogin(String code);
}