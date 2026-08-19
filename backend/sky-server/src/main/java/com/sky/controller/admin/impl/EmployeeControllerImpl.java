package com.sky.controller.admin.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.controller.admin.EmployeeController;
import com.sky.service.admin.EmployeeService;
import entity.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import result.Result;

@RestController
@RequestMapping("/api")
@Tag(name="员工信息管理")
public class EmployeeControllerImpl implements EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Override
    @PostMapping("/employee/add")
    @Operation(summary="新增员工")
   public Result<String> addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
        return new Result<>(200,"员工添加成功",null);
    }


    @Override
    @PutMapping("/employee/update")
    @Operation(summary="修改员工信息")
    public Result<String> updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
        return new Result<>(200,"员工信息修改成功",null);
    }


    @Override
    @GetMapping("/employee/lists")
    @Operation(summary="员工分页查询")
    public Result<Page<Employee>> selectPage( @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        return new Result<>(200,"分页查询成功",employeeService.selectPage(currentPage,pageSize));
    }


    @Override
    @PostMapping("/statusChange")
    @Operation(summary="修改员工状态")
    public Result<Integer> statusChange(@RequestBody Integer id) {
        return new Result<>(200,"员工状态修改成功",employeeService.statusChange(id));
    }


    @Override
    @GetMapping("/employee/list")
    @Operation(summary="根据员工姓名进行分页查询")
    public Result<Page<Employee>> nameSelectPage(@RequestParam String employeeName,@RequestParam Integer currentPage,@RequestParam  Integer pageSize){
       return new Result<>(200,"分页查询成功", employeeService.nameSelectPage(employeeName,currentPage,pageSize));
    }
    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }
    }


