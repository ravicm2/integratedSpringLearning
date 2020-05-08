package com.springboot.example.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BaseWebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests() //authorize the requests
                .antMatchers("index", "/", "/css/*", "/js/*") // for all these uri matches
                .permitAll() //permit all the uri above
                .antMatchers("/api/**").hasRole(UserRoles.ADMIN.name()) //making this api should be handled only by admins
                .anyRequest() //any request
                .authenticated() // must be authenticated
                .and() //and
                .httpBasic(); // i want to use basic auth
    }

    @Override
    @Bean // to instantiate UserDetailsService.
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("ashwin")
//                .password("Ash!995")
                .password(passwordEncoder.encode("Ash!995"))
                .roles(UserRoles.USER.name())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(UserRoles.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(user, adminUser);
    }
}
