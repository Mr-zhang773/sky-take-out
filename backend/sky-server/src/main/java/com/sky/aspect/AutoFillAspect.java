package com.sky.aspect;

import entity.Category;
import entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Aspect
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.service.impl.*.*(..))")
    public void servicePointcut() {
    }

    @Around("servicePointcut()")
    public Object autoFill(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        boolean isAdd = "addEmployee".equals(methodName) || "categoryAdd".equals(methodName);
        boolean isUpdate = "updateEmployee".equals(methodName) || "categoryUpdate".equals(methodName);

        if ((isAdd || isUpdate) && args != null && args.length > 0) {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            for (Object arg : args) {
                if (arg instanceof Employee) {
                    Employee employee = (Employee) arg;
                    if (isAdd) {
                        employee.setCreateTime(now);
                        employee.setUpdateTime(now);
                        log.info("addEmployee 自动填充时间");
                    } else if (isUpdate) {
                        employee.setUpdateTime(now);
                        log.info("updateEmployee 自动填充更新时间");
                    }
                }

                if (arg instanceof Category) {
                    Category category = (Category) arg;
                    if (isAdd) {
                        category.setCreateTime(now);
                        category.setUpdateTime(now);
                        log.info("categoryAdd 自动填充时间");
                    } else if (isUpdate) {
                        category.setUpdateTime(now);
                        log.info("categoryUpdate 自动填充更新时间");
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}