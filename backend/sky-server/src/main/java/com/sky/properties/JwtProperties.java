package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtProperties {
    private String secret;
    private Long ttl;   // ✅ 改成 Long 类型
}