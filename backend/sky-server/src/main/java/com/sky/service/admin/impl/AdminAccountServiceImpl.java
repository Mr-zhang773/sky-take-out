    package com.sky.service.admin.impl;

    import com.sky.mapper.AdminAccountMapper;
    import com.sky.service.admin.AdminAccountService;
    import com.sky.utils.JwtUtil;
    import entity.Admin;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.HashMap;
    import java.util.Map;

    @Service
    public class AdminAccountServiceImpl implements AdminAccountService {
        @Autowired
        AdminAccountMapper adminAccountMapper;
        @Autowired
        JwtUtil jwtUtil;
        @Override
        public Integer adminRegister(Admin admin) {
            return adminAccountMapper.adminRegister(admin);
        }
        @Override
        public String adminLogin(Admin admin) {
            Map<String,Object> map = new HashMap<>();
            map.put("adminAccount",admin.getAdminAccount());
            if(adminAccountMapper.adminLogin(admin) != null) {
                return jwtUtil.createToken(map);
            }else return "登陆失败";
        }
    }
