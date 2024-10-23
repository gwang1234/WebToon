package org.example.webtoonepics.public_method.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("http://webtoonepic.site:3000", "http://localhost:3000", "http://43.203.42.241:3000",
                        "http://ec2-43-203-42-241.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 허용할 메소드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 인증 정보 허용
                .maxAge(3600); // 1시간 동안 Preflight 요청 캐시;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // FormHttpMessageConverter를 추가하여 x-www-form-urlencoded 데이터를 처리
        converters.add(new FormHttpMessageConverter());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//
//        corsRegistry.addMapping("/**")
//                .allowedOrigins("http://webtoonepic.site:3000", "http://localhost:3000",
//                        "http://43.203.42.241:8080", "http://ec2-43-203-42-241.ap-northeast-2.compute.amazonaws.com:8080",
//                        "http://43.203.42.241:3000", "http://ec2-43-203-42-241.ap-northeast-2.compute.amazonaws.com:3000") // 허용할 출처
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 허용할 메소드
//                .allowedHeaders("*") // 모든 헤더 허용
//                .allowCredentials(true) // 인증 정보 허용
//                .maxAge(3600); // 1시간 동안 Preflight 요청 캐시
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // FormHttpMessageConverter를 추가하여 x-www-form-urlencoded 데이터를 처리
//        converters.add(new FormHttpMessageConverter());
//    }
}
