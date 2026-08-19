package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminAccountMapper extends BaseMapper<Admin> {
    @Insert("INSERT INTO admin (admin_account, admin_password) VALUES (#{adminAccount}, #{adminPassword})")
    Integer adminRegister(Admin admin);
    @Select("SELECT * FROM admin WHERE admin_account = #{adminAccount} AND admin_password = #{adminPassword}")
    Integer adminLogin(Admin admin);
}
