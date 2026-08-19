package com.sky.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        // 定义安全方案（Bearer Token）
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // 全局安全要求
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖接口文档")
                        .description("sky-take-out-api")
                        .version("1.0"))
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}