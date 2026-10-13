package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wechat_user")
public class WechatUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String openid;

    private String sessionKey;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}