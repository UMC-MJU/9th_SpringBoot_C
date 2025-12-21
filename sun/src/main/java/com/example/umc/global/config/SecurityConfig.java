package com.example.umc.global.config;

import com.example.umc.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // Spring Security 설정을 활성화시키는 역할
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            // 회원가입, 로그인 허용
            "/api/members/signup",
            "/api/members/login",
    };

    /**
     *이 메소드는 `SecurityFilterChain`을 정의합니다. `HttpSecurity` 객체를 통해 다양한 보안 설정을 구성할 수 있습니다.
     *
     * - `authorizeHttpRequests()` 는 HTTP 요청에 대한 접근 제어를 설정합니다.
     *     - `requestMatchers()` 메소드를 사용하여 특정 URL 패턴에 대한 접근 권한을 설정합니다.
     *     - `permitAll()`은 인증 없이 접근 가능한 경로를 지정합니다.
     *     - `hasRole("ADMIN")`은 'ADMIN' 역할을 가진 사용자만 접근 가능하도록 제한합니다.
     *     - `anyRequest().authenticated()`는 그 외 모든 요청에 대해 인증을 요구합니다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (REST API용)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // JWT 사용으로 세션 미사용
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder 빈
     * BCrypt 해시 함수를 사용하여 비밀번호를 암호화
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
