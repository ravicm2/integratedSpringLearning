package com.springboot.example.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.springboot.example.springbootdemo.security.UserRoles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BaseWebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests() //authorize the requests
                .antMatchers("index", "/", "/css/*", "/js/*") // for all these uri matches
                .permitAll() //permit all the uri above
                .antMatchers("/api/**").hasRole(ADMIN.name()) //making this api should be handled only by admins
                .anyRequest() //any request
                .authenticated() // must be authenticated
                .and() //and
//                .httpBasic(); // i want to use basic auth
                .formLogin()// i want to use form login
                .loginPage("/login").permitAll(); // want to use my own login page.
    }


    @Override
    @Bean // to instantiate UserDetailsService.
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("ashwin")
//                .password("Ash!995")
                .password(passwordEncoder.encode("Ash!995"))
//                .roles(UserRoles.USER.name()) //the role will be ROLE_USER
                .authorities(USER.getGrantedAuthorities()) //here we assign role and assigned authorities for the role
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
//                .roles(UserRoles.ADMIN.name()) //the role will be ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities()) //here we assign role and assigned authorities for the role
                .build();

        UserDetails adminTrainee = User.builder()
                .username("trainee")
                .password(passwordEncoder.encode("admin_trainee"))
//                .roles(UserRoles.ADMINTRAINEE.name()) //the role will be ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities()) //here we assign role and assigned authorities for the role
                .build();

        return new InMemoryUserDetailsManager(user, adminUser, adminTrainee);
    }
}
