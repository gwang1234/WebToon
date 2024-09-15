package org.example.webtoonepics.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.jwt.JWTFilter;
import org.example.webtoonepics.jwt.JWTUtil;
import org.example.webtoonepics.service.CustomOAuth2UserService;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
//        this.authenticationConfiguration = authenticationConfiguration;
//        this.jwtUtil = jwtUtil;
//    }

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
                        authorizeRequests
                                .requestMatchers("/", "/login", "/oauth2/**", "/css/**", "/js/**", "/images/**",
                                        "/jwt-auth")
                                .permitAll()
                                .requestMatchers("/jwt-token").hasRole("USER")
                                .anyRequest().authenticated()
                )

                // JWT와 OAuth2 로그인 필터 충돌 방지
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

                // 소셜 로그인(OAuth2) 관련 설정
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(
                                        userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
                                .defaultSuccessUrl("/loginInfo", true)
                                .failureUrl("/loginFail")
                )

                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                )

                // CORS 설정
                .cors(corsCustomizer -> corsCustomizer.configurationSource(
                        new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();
                                configuration.setAllowedOrigins(
                                        Collections.singletonList(
                                                "http://localhost:3000"));  // CORS 설정 수정
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);
                                configuration.setExposedHeaders(
                                        Collections.singletonList("Authorization"));
                                return configuration;
                            }
                        }))

                .csrf(csrf -> csrf.disable())  // JWT를 사용하므로 CSRF는 비활성화
                .formLogin(formLogin -> formLogin.disable())  // formLogin 비활성화
                .httpBasic(httpBasic -> httpBasic.disable());  // HTTP Basic 비활성화

        // 세션 관리 정책 설정: OAuth2 로그인 시 세션을 사용해야 함
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));  // OAuth2 로그인을 위해 세션 사용

        return http.build();
    }
}
