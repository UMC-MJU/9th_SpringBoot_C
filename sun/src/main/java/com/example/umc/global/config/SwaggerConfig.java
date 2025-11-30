package com.example.umc.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // API 기본 정보
        Info info = new Info()
                .title("UMC 9기 워크북 API")
                .description("UMC 9기 Spring Boot 워크북 API 문서")
                .version("v1.0.0");

        // 현재 인증 방식: X-Member-Id 헤더
        String securitySchemeName = "X-Member-Id";
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        Components components = new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .description("회원 ID를 헤더에 포함 (추후 JWT로 교체 예정)"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/").description("Default Server"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    /*
     * 추후 JWT 도입 시 아래 코드로 교체
     *
    @Bean
    public OpenAPI openAPIWithJWT() {
        Info info = new Info()
                .title("UMC 9기 워크북 API")
                .description("UMC 9기 Spring Boot 워크북 API 문서")
                .version("v1.0.0");

        String securitySchemeName = "Bearer Authentication";
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        Components components = new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT 토큰을 입력하세요"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/").description("Default Server"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    */
}
