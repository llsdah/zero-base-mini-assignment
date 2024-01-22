package com.example.zerobase.zerobaseminiassignment.config;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Slf4j
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private MyJwtProvider jwtProvider;
    @Autowired
    private MyJwtFilter myJwtFilter;

    @Override
    public void configure(HttpSecurity http) {

        // security 로직에 JwtFilter 등록
        http.addFilterBefore(
                new MyJwtFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("filterChain");
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/members/**").permitAll()
                                .requestMatchers("/hashTags/**").permitAll()
                                .requestMatchers("/posts/**").permitAll()
                                .requestMatchers("/site/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(myJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 또는 별도의 CorsConfigurationSource 빈을 설정할 수도 있습니다.
    // 이 경우 해당 빈이 적용되며 http.cors()를 사용하지 않아도 됩니다.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");  // 모든 origin 허용 (실제 운영에서는 필요에 따라 수정)

        // 다른 CORS 설정 추가 가능
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
