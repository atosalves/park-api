package com.atosalves.park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.atosalves.park_api.jwt.JwtAuthenticationEntryPoint;
import com.atosalves.park_api.jwt.JwtAuthorizationFilter;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SpringSecurityConfig {

        private static final String[] DOCUMENTATION_OPENAPI = {
                        "/docs/index.html",
                        "/docs-park.html", "/docs-park/**",
                        "/v3/api-docs/**",
                        "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
                        "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
        };

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.POST, "api/v1/users").permitAll()
                                                .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
                                                .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(handler -> handler
                                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                                .build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        @Bean
        JwtAuthorizationFilter jwtAuthorizationFilter() {
                return new JwtAuthorizationFilter();
        }

}
