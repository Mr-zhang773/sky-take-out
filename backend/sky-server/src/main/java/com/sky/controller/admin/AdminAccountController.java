package com.sky.controller.admin;

import entity.Admin;
import org.springframework.web.bind.annotation.RequestBody;
import result.Result;

public interface AdminAccountController {
 Result<Integer> adminRegister(@RequestBody Admin admin);
 Result<String> adminLogin(@RequestBody Admin admin);
 String testRedis();
}
