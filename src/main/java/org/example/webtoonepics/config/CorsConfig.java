package org.example.webtoonepics.config;

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
                .allowedOrigins("http://localhost:3000");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // FormHttpMessageConverter를 추가하여 x-www-form-urlencoded 데이터를 처리
        converters.add(new FormHttpMessageConverter());
    }
}
