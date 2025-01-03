package org.example.webtoonepics.public_method.config;

import jakarta.servlet.http.HttpServletRequest;
import org.example.webtoonepics.jwt_login.jwt.JWTFilter;
import org.example.webtoonepics.jwt_login.jwt.JWTUtil;
import org.example.webtoonepics.jwt_login.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,
            RedisTemplate<String, String> redisTemplate) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public HttpSessionOAuth2AuthorizationRequestRepository httpSessionOAuth2AuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests // 사용자가 보내는 요청에 인증 절차 수행 필요
                                        .requestMatchers("/", "/login", "/oauth2/**", "/css/**", "/js/**", "/images/**",
                                                "/jwt-login", "/jwt-auth", "jwt-refresh", "/api/**")
                                        .permitAll() // 인증이 필요 없는 경로
//                                .requestMatchers("/jwt-token").hasRole("USER")
//                                .anyRequest().authenticated() // 모든 요청은 인증 필요
                                        .anyRequest().permitAll()

                )
                .oauth2Login(auth2Login ->
                        auth2Login // OAuth2를 통한 로그인 사용
                                .authorizationEndpoint(endpointConfig ->
                                        endpointConfig.authorizationRequestRepository(
                                                httpSessionOAuth2AuthorizationRequestRepository()))
                                .defaultSuccessUrl("/oauth2/login", true) // 로그인 성공 시 redirect
                )
                .logout(logout ->
                        logout // 로그아웃 설정
                                .logoutSuccessUrl("/login") // 로그아웃 성공 후 리다이렉트 경로
                                .invalidateHttpSession(true) // 세션 무효화
                );
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        // 여러 도메인 허용
                        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://webtoonepic.site:3000",
                                "http://43.203.42.241:3000",
                                "http://ec2-43-203-42-241.ap-northeast-2.compute.amazonaws.com:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());
        http
                .addFilterBefore(new JWTFilter(jwtUtil, redisTemplate), LoginFilter.class);
        http
                .addFilterBefore(new JWTFilter(jwtUtil, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // 필터 추가 loginFilter()는 인자를 받음
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }
}