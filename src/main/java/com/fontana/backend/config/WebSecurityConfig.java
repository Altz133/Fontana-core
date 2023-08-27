package com.fontana.backend.config;

import com.fontana.backend.role.entity.RoleType;
import com.fontana.backend.security.filters.JwtAuthenticationFilter;
import com.fontana.backend.security.filters.SessionStatusFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.fontana.backend.config.RestEndpoints.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final SessionStatusFilter sessionStatusFilter;

    /**
     * This method provides various security configuration solutions, including protection against CSRF attacks, CORS
     * configuration, session & jwt management, and granting http requests access (or not).
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
                    configuration.setAllowedOriginPatterns(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setMaxAge(Duration.of(1L, ChronoUnit.HOURS));
                    return configuration;
                }))
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(new AntPathRequestMatcher(AUTH + "/*")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher(SESSION + "/*")).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())

                                .requestMatchers(new AntPathRequestMatcher(DMX + "/update/*")).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(DMX + DMX_CHANGE_API_STATUS)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(DMX + DMX_CHANGE_PUMP_POWER_MULTIPLIER)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(DMX + DMX_PANIC)).hasAnyAuthority(
                                        RoleType.ADMIN.name())
                                .requestMatchers(new AntPathRequestMatcher(DMX + DMX_UPDATE_DMX_ADDRESSES)).hasAnyAuthority(
                                        RoleType.ADMIN.name())

                                .requestMatchers(new AntPathRequestMatcher(SCHEDULE + SCHEDULE_ADD)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(SCHEDULE + SCHEDULE_UPDATE)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(SCHEDULE + SCHEDULE_DELETE)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .requestMatchers(new AntPathRequestMatcher(SCHEDULE + SCHEDULE_STOP)).hasAnyAuthority(
                                        RoleType.ADMIN.name(), RoleType.OPERATOR.name())
                                .anyRequest().authenticated())
                //TODO keep adding every endpoint/group of endpoints with proper access level
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(sessionStatusFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
