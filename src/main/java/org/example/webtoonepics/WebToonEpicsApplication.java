package org.example.webtoonepics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebToonEpicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebToonEpicsApplication.class, args);
    }

}
