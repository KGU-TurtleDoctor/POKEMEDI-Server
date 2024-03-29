package com.turtledoctor.kgu.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {

    @Value("${corsURL}")
    String url;

    @Bean
    public WebMvcConfigurer corsMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {

                corsRegistry.addMapping("/**")
                        .exposedHeaders("Authorization")
                        .allowedOriginPatterns(url)
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .allowedMethods("*") // cors 에러 해결 테스트
                        .maxAge(3000);
            }
        };
    }


}
