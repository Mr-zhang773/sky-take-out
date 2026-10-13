package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@TableName("order_info")
public class Order {


    @TableId(type = IdType.AUTO)
    private Long id;


    // 用户id
    Long  userId;


    // 收货人
    private String consigneeName;



    // 电话
    private String consigneePhone;


    // 地址
    private String consigneeAddress;


    // 总金额
    private BigDecimal totalPrice;


    // 订单状态
    private String status;


    // 备注
    private String remark;

    // 客户端订单请求唯一标识
    private String requestId;

    private String orderNo;


    private LocalDateTime createTime;


    private LocalDateTime updateTime;

}