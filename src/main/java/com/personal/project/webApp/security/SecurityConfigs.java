package com.personal.project.webApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.Security;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

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
                        .requestMatchers("/images/**","/css/**","/temps/cart/**","/temps/add-to-cart","/temps/add-account","/temps/list","/","index.html")
                        .permitAll()
                        .requestMatchers("/temps/cart/**","/temps/shopping-list","/","index.html").hasRole("CUSTOMER")
                        .requestMatchers("/temps/update","/**","/","/temps/accounts","/temps/add-product","/temps/add").hasRole("ADMIN")



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
                "select email, role from roles where email=?"
        );

        return jdbcUserDetailsManager;
    }

}
