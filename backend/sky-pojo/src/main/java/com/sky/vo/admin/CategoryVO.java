package com.sky.vo.admin;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CategoryVO {


    private Integer id;


    private String categoryName;


    private Integer categoryType;


    private Integer status;


    private Integer sort;


    private LocalDateTime updateTime;

}