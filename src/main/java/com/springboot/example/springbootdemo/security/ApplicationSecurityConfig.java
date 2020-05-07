package com.springboot.example.springbootdemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests() //authorize the requests
                .antMatchers("index", "/", "/css/*", "/js/*") // for all these uri matches
                .permitAll() //permit all the uri above
                .anyRequest() //any request
                .authenticated() // must be authenticated
                .and() //and
                .httpBasic(); // i want to use basic auth
    }
}
