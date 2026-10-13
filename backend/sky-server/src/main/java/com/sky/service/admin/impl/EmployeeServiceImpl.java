package com.sky.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.service.admin.EmployeeService;
import com.sky.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.mapper.EmployeeMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Integer updateEmployee(Employee employee) {
        return employeeMapper.updateById(employee);
    }


    @Override
    public  Page<Employee> selectPage(Integer currentPage, Integer pageSize) {
       Page<Employee> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Employee::getId);  // 按 id 升序
       return  employeeMapper.selectPage(page,wrapper);
    }


    @Override
    public Integer addEmployee(Employee employee) {
        return employeeMapper.insert(employee);
    }


    @Override
    public Integer statusChange(Integer id) {
        return employeeMapper.statusChange(id);
    }


    @Override
    public Page<Employee> nameSelectPage(String employeeName,Integer currentPage,Integer pageSize) {
        Page<Employee> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Employee::getId);
        if (employeeName != null && !employeeName.trim().isEmpty()) {
            wrapper.like(Employee::getEmployeeName, employeeName);
            return employeeMapper.selectPage(page,wrapper);
        }else return employeeMapper.selectPage(new Page<>(currentPage,pageSize),null);
    }

}
