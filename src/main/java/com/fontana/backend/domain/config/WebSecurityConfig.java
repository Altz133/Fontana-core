package com.fontana.backend.domain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Description: spring security configuration
 * @author: Sebastian Mazur
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    /**
     * This method provides various security configuration solutions, including protection against CSRF attacks, CORS
     * configuration, session & jwt management and granting http requests access (or not).
     *
     * @param http The HttpSecurity object used to configure the security settings for HTTP requests.
     * @return configured SecurityFilterChain for the application's HTTP security.
     * @throws Exception If an exception occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)  //FIXME configure csrf as it should not be disabled
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("localhost:4200","http://localhost:4200"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setMaxAge(Duration.of(1L, ChronoUnit.HOURS));
                    return configuration;
                })).authorizeHttpRequests(requests ->
                requests.anyRequest().permitAll())
                .headers()
                .frameOptions()
                .sameOrigin();

        return http.build();
    }
}