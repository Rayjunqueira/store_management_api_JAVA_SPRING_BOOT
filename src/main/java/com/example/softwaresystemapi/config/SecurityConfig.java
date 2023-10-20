package com.example.softwaresystemapi.config;

import com.example.softwaresystemapi.security.FilterToken;
import com.example.softwaresystemapi.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public FilterToken filterTokenJwt() {
        return new FilterToken();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authorizeConfig -> {
                            authorizeConfig.requestMatchers(HttpMethod.OPTIONS).permitAll();
                            authorizeConfig.requestMatchers(HttpMethod.POST, "/user").permitAll();
                            authorizeConfig.requestMatchers(HttpMethod.POST, "/auth").permitAll();
                            authorizeConfig.requestMatchers(HttpMethod.POST, "/auth**").permitAll();
                            authorizeConfig.requestMatchers( "/category").authenticated();
                            authorizeConfig.requestMatchers( "/product").authenticated();
                            authorizeConfig.requestMatchers( "/product/**").authenticated();
                            authorizeConfig.requestMatchers( "/order").authenticated();
                            authorizeConfig.requestMatchers( "/order/**").authenticated();
                            authorizeConfig.requestMatchers( "/customercategory").authenticated();
                            authorizeConfig.requestMatchers( "/customercategory/**").authenticated();
                            authorizeConfig.requestMatchers( "/searchorders/**").authenticated();
                            authorizeConfig.requestMatchers( "/searchorders").authenticated();
                            authorizeConfig.anyRequest().authenticated();
                        })
                .userDetailsService(userDetailsService);
        http.addFilterBefore(filterTokenJwt(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}