package com.sky.vo.user;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO {
    private String orderNo;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeAddress;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderDetailVO> orderDetails;
    private LocalDateTime createTime;
    private String remark;
}
