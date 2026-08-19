package com.sky;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import result.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return new Result<>(500,e.getMessage(),null);
    }

}
