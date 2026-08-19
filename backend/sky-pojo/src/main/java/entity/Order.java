    package entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import lombok.Data;

    import java.math.BigDecimal;

    @Data
    @TableName("order_info")
    public class Order {
        @TableId(type = IdType.AUTO)
        private Long id;
        private String consigneeName;
        private Integer userId;
        private String consigneeAddress;
        private String consigneePhone;
        private String dishes;  // JSON字符串
        private BigDecimal totalPrice;
        private String status;
        private String createTime;
        private String updateTime;
        private String createUser;
        private String updateUser;
    }