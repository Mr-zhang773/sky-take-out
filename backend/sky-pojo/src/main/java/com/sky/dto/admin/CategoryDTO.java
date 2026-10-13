package com.sky.dto.admin;

import lombok.Data;

@Data
public class CategoryDTO {

    private Integer id;

    private String categoryName;

    private Integer categoryType;

    private Integer status;

    private Integer sort;

}