package org.example.webtoonepics.config;

import org.example.webtoonepics.service.KakaoOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 보안 설정 사용 X
                .formLogin().disable()
                .authorizeRequests() // 사용자가 보내는 요청에 인증 절차 수행 필요
                .requestMatchers("/", "/login**", "/css/**", "/js/**", "/images/**").permitAll() // 인증이 필요 없는 경로
                .anyRequest().authenticated() // 모든 요청은 인증 필요
                .and()
                
                .oauth2Login() // OAuth2를 통한 로그인 사용
                .defaultSuccessUrl("/oauth2/kakao", true) // 로그인 성공 시 redirect
                .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트 경로
                .userInfoEndpoint() // 사용자가 로그인에 성공하였을 경우
                .userService(oAuth2UserService()) // OAuth2 사용자 정보를 처리하는 서비스
                .and().and()
                
                .logout() // 로그아웃 설정
                .logoutUrl("/logout") // 로그아웃 처리 경로
                .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트 경로
                .invalidateHttpSession(true) // 세션 무효화
                .and()
                
                .exceptionHandling() // 예외 처리 설정
                .accessDeniedPage("/403"); // 접근이 거부된 경우 리다이렉트할 페이지
        
        return http.build();
    }
    
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(WebClient.Builder webClientBuilder) {
        return new KakaoOAuth2UserService(webClientBuilder); // 사용자 정보를 처리할 서비스
    }
}
