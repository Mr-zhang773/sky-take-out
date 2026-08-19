package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wechat_user")
public class WechatUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String openid;
    private String sessionKey;
    private String createTime;
    private String updateTime;
}