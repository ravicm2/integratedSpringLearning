package com.springboot.example.springbootdemo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Step 2.
 * Job of this class is to validate credentials.
 * Spring security does it by default and we are overriding it and providing our own implementation.
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword());

            authenticationManager.authenticate(authentication);
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Step 3
     * job of this method is to create a token and send back to client.
     * This method will never be executed when the above method
     * {@link JwtUsernameAndPasswordAuthenticationFilter#attemptAuthentication} fails.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";

        //create token
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                //token body
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                //signature and bytes should be something very long where it prevents from unlocking the secret key
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();

        //send token to the client
        response.addHeader("Authorizatin", "Bearer " + token);
    }
}

