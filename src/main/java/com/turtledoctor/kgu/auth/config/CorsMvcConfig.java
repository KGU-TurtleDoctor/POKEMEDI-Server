package com.turtledoctor.kgu.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {

    @Value("${redirectURL}")
    String url;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .exposedHeaders("*")
                .allowedOrigins(url)
                .allowedMethods("*"); // cors 에러 해결 테스트
    }
}
