 package com.sky.vo.admin;

import com.sky.vo.admin.OrderDetailVO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家端订单视图对象
 */
@Data
public class OrderVO {

    /** 订单ID */
    private Long id;

    /** 订单编号 */
    private String orderNo;

    /** 收货人姓名 */
    private String consigneeName;

    /** 收货人电话 */
    private String consigneePhone;

    /** 收货地址 */
    private String consigneeAddress;

    /** 订单总价 */
    private BigDecimal totalPrice;

    /** 订单状态 */
    private String status;

    /** 下单时间 */
    private LocalDateTime createTime;

    /** 订单菜品详情 */
    private List<OrderDetailVO> orderDetails;

    private String remark;
}
