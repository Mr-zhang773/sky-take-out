package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dish")
public class Dish {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String dishName;
    private String  dishImage;
    private  Integer categoryId;
    private  Integer dishPrice;
    private String createTime;
    private String updateTime;
    private Integer status;
    @TableField(exist = false)  // ← 必须加！表示数据库里没有这个字段
    private String categoryName;
}
