package com.sky.service.admin;

import com.sky.entity.Admin;

public interface AdminAccountService {
    Integer adminRegister(Admin admin);
    String adminLogin(Admin admin);
}
