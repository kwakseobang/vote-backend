package com.kwakmunsu.vote.config;


import com.kwakmunsu.vote.jwt.JWTFilter;
import com.kwakmunsu.vote.jwt.JWTProvider;
import com.kwakmunsu.vote.jwt.handler.JwtAccessDeniedHandler;
import com.kwakmunsu.vote.jwt.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 시큐리티에서 관리하게 됨
public class SecurityConfig {
    private final String[] adminUrl = {"/admin/**"};
    private final String[] permitAllUrl = {"/","/error","/login","/signup","/reissue"};
    private final String[] hasRoleUrl = {"/vote/**"};

    private final JWTFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 비활성화 필터
        http
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                .csrf((auth) -> auth.disable())
                .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 인가
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(permitAllUrl).permitAll() //모두 허용
                        .requestMatchers(adminUrl).hasRole("ADMIN") //
                        .requestMatchers(hasRoleUrl).hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated() // 위에서 처리 못한 나머지 경로를 로그인한 사용자만 접근 허용.
                        // 상단부터 check하게 됨.
                );
        // 예외 처리
        http
         .exceptionHandling(handle -> handle
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        );

        // 필터 적용
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
