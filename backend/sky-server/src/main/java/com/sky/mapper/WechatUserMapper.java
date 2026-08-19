package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.WechatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUser> {
}