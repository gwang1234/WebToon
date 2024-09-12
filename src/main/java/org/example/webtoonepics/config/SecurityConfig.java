package org.example.webtoonepics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests // 사용자가 보내는 요청에 인증 절차 수행 필요
                                .requestMatchers("/", "/login", "/oauth2/**", "/css/**", "/js/**", "/images/**")
                                .permitAll() // 인증이 필요 없는 경로
                                .anyRequest().authenticated() // 모든 요청은 인증 필요
                )

                .oauth2Login(auth2Login ->
                        auth2Login // OAuth2를 통한 로그인 사용
                                .defaultSuccessUrl("/loginInfo", true) // 로그인 성공 시 redirect
                                .failureUrl("/loginFail") // 로그인 실패 시 리다이렉트 경로
                )

                .logout(logout ->
                        logout // 로그아웃 설정
                                .logoutSuccessUrl("/login") // 로그아웃 성공 후 리다이렉트 경로
                                .invalidateHttpSession(true) // 세션 무효화
                );

        return http.build();
    }
}
