package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String categoryName;
    private Integer CategoryType;
    private  Integer categoryId;
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;
    private Integer status;
}
