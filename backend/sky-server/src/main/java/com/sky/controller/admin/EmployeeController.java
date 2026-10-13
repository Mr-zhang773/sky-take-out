package com.sky.controller.admin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.entity.Employee;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import result.Result;

public interface EmployeeController {
    Result<String> addEmployee(Employee employee);
    Result<String> updateEmployee(@RequestBody Employee employee);
    Result<Page<Employee>> selectPage( @RequestParam Integer currentPage, @RequestParam Integer pageSize);
    Result<Integer> statusChange(@RequestBody Integer id);
    Result<Page<Employee>> nameSelectPage(@RequestParam String employeeName,@RequestParam Integer currentPage,@RequestParam  Integer pageSize);
}
