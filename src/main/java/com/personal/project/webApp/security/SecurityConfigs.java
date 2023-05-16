package com.personal.project.webApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.Security;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
/*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails zakaria = User.builder()
                .username("zakaria")
                .password("{noop}admin")
                .roles("CUSTOMER","MANAGER","ADMIN")
                .build();
        UserDetails user1 = User.builder()
                .username("user1")
                .password("{noop}user1")
                .roles("CUSTOMER")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password("{noop}user2")
                .roles("COSTUMER","CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(zakaria,user1,user2);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(config ->
                config
                        .requestMatchers("/temps/list","/images/**","/css/**","/temps/cart/**","/**","/temps/add-to-cart","/temps/add-account")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/temps/cart/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/temps/update","/temps/list","/images/**","/css/**","/temps/cart/**","/**","/temps/add-to-cart","temps/shopping-list", "temps/Add-product", "temps/delete-product").hasRole("ADMIN")

        ).formLogin((form) -> form
                .loginPage("/login")
                .permitAll())
                .logout((logout)->logout.permitAll());
        httpSecurity.httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsManager userDetailsManagerDataBase(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, enabled from customer where email=?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select email, role from customer where email=?"
        );
        return jdbcUserDetailsManager;
    }
}
