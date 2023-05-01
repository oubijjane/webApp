package com.personal.project.webApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails zakaria = User.builder()
                .username("zakaria")
                .password("{noop}admin")
                .roles("COSTUMER","MANAGER","ADMIN")
                .build();
        UserDetails user1 = User.builder()
                .username("user1")
                .password("{noop}user1")
                .roles("COSTUMER")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password("{noop}user2")
                .roles("COSTUMER","MANAGER")
                .build();

        return new InMemoryUserDetailsManager(zakaria,user1,user2);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(config ->
                config
                        .requestMatchers(HttpMethod.GET,"/temps/list","/images/**","/css/**","/temps/cart/**","/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/temps/cart/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST,"/temps/update").hasRole("ADMIN")

        ).formLogin((form) -> form
                .loginPage("/login")
                .permitAll())
                .logout((logout)->logout.permitAll());
        httpSecurity.httpBasic();
        return httpSecurity.build();
    }
}
