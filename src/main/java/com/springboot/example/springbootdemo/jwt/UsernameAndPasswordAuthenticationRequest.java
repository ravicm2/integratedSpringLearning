package com.springboot.example.springbootdemo.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Step 1
 * job of this class is to capture the username and password
 * sent by the client
 */
@NoArgsConstructor
@Data
public class UsernameAndPasswordAuthenticationRequest {

    private String username;

    private String password;

}
