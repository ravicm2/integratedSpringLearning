package com.springboot.example.springbootdemo.security;

import com.springboot.example.springbootdemo.auth.ApplicationUserService;
import com.springboot.example.springbootdemo.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.springboot.example.springbootdemo.security.UserRoles.ADMIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // for annotation validation
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public BaseWebSecurityConfig(PasswordEncoder passwordEncoder,
                                 ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                //STEP 5: making jwt stateless as it is one of the pros. 'check readme.md'
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // STEP4: adding filter to perform jwt auth.
                // And we got authenticationManager() from WebSecurityConfigurerAdapter
                // which extends BaseWebSecurityConfig.
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .authorizeRequests() //authorize the requests
                .antMatchers("index", "/", "/css/*", "/js/*") // for all these uri matches
                .permitAll() //permit all the uri above
                .antMatchers("/api/**").hasRole(ADMIN.name()) //making this api should be handled only by admins
                .anyRequest() //any request
                .authenticated(); // must be authenticated
//                .and() //and
//                .formLogin()

//                .httpBasic(); // i want to use basic auth
//                .formLogin();// i want to use form login
        //.loginPage("/login").permitAll(); // want to use my own login page.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserService);
        return daoAuthenticationProvider;
    }
}
