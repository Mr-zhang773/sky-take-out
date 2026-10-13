package com.sky.dto.user;

import lombok.Data;

@Data
public class OrderDto {


    //收货人
    private String consigneeName;


    //电话
    private String consigneePhone;


    //地址
    private String consigneeAddress;

    private String requestId;


    //商品信息
    private String dishes;



    //备注
    private String remark;

}