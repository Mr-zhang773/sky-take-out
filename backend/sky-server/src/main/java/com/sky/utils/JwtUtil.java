package com.sky.utils;

import com.sky.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {


    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 获取签名密钥
     */
    private Key getSigningKey() {

        String secret = jwtProperties.getSecret();

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }



    /**
     * 创建JWT令牌
     */
    public String createToken(Map<String, Object> claims) {


        Long ttl = jwtProperties.getTtl();


        if (ttl == null) {

            ttl = 3600000L; // 默认1小时

        }


        Date now = new Date();

        Date expiration =
                new Date(now.getTime() + ttl);



        return Jwts.builder()

                // JWT载荷数据
                .setClaims(claims)

                // 签发时间
                .setIssuedAt(now)

                // 过期时间
                .setExpiration(expiration)

                // HS256签名
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();

    }



    /**
     * 解析JWT
     */
    public Claims parseToken(String token) {


        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }



    /**
     * 验证JWT是否有效
     */
    public boolean validateToken(String token) {


        try {

            parseToken(token);

            return true;


        } catch (Exception e) {

            return false;

        }

    }



    /**
     * 从JWT中获取用户ID
     */
    public Long getUserIdFromToken(String token) {


        Claims claims = parseToken(token);


        return claims.get(
                "userId",
                Long.class
        );

    }

}