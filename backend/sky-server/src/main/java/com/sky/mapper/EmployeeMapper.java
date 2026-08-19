package com.sky.mapper;

import com.sky.annotation.AutoFill;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Employee;
import entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {


    int updateEmployee(Employee employee);
     @AutoFill
     @Update("UPDATE employee SET status = CASE status WHEN 1 THEN 0 ELSE 1 END WHERE id = #{id}")
     Integer statusChange(Integer id);}
