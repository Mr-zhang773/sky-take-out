package com.sky.service.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Employee;

public interface EmployeeService {
    Integer addEmployee(Employee employee);
    Integer statusChange(Integer id);
    Integer updateEmployee(Employee employee);
    Page<Employee> selectPage(Integer currentPage, Integer pageSize);
    Page<Employee> nameSelectPage(String employeeName,Integer currentPage,Integer pageSize );
}
