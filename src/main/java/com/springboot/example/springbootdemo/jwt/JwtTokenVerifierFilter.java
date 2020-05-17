package com.springboot.example.springbootdemo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    /**
     * Step 4
     * Job of this method is to verify the incoming token from the client.
     * <p>
     * it will get invoked once for every single request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // same header key we passed in STEP 3:
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        //when authorizationHeader is null or empty or it didn't start with 'Bearer '.
        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

        // remove Bearer from token and take only the actual token
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            // same secretKey passed to encode
            String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();

            //same key has passed when authorities set through claim in step 3:
            // var is from java 9 . instead typing long declaration type . i used var
            var authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(value -> new SimpleGrantedAuthority(value.get("authority"))).collect(Collectors.toSet());

            //From this point we are setting the user is authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        //STEP 5: passing the request and response to the next filter in the chain
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
