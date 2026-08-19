package com.sky.service.admin;

import entity.Admin;

public interface AdminAccountService {
    Integer adminRegister(Admin admin);
    String adminLogin(Admin admin);
}
