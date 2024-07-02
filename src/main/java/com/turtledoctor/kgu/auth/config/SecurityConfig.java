package com.turtledoctor.kgu.auth.config;

import com.turtledoctor.kgu.auth.exception.CustomAuthenticationEntryPoint;
import com.turtledoctor.kgu.auth.jwt.CustomLogoutFilter;
import com.turtledoctor.kgu.auth.jwt.JWTFilter;
import com.turtledoctor.kgu.auth.jwt.JWTUtil;
//import com.turtledoctor.kgu.auth.jwt.JWTExceptionFilter;
import com.turtledoctor.kgu.auth.oauth2.CustomSuccessHandler;
import com.turtledoctor.kgu.auth.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService; // 생성자를 통해 객체 주입
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    //private final JWTExceptionFilter jwtExceptionFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Value("${corsURL}")
    String url;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil,/* JWTExceptionFilter jwtExceptionFilter,*/ CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        //this.jwtExceptionFilter = jwtExceptionFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        //configuration.setAllowedOrigins(Collections.singletonList(url));
                        configuration.setAllowedOrigins(Arrays.asList(url,"http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        return configuration;
                    }
                }));

        //csrf disable
        http

                .csrf(AbstractHttpConfigurer::disable);
        //From 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        //HTTP Basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);


        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)

                );

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/error", "/api/isLogin").permitAll()
                        .anyRequest().authenticated());

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 인증 실패 시 CustomAuthenticationEntryPoint 사용
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)); // Registering CustomAuthenticationEntryPoint

        //JWTFilter 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil), LogoutFilter.class);


        return http.build();
    }

    //로그인 여부 체크에 한해서만, springSecurity 필터 거치지 않도록 해결.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers(
                            "/api/isLogin"
                    );
        };
    }
}


