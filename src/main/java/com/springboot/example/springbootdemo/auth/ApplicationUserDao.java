package com.springboot.example.springbootdemo.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUsers> selectApplicationUsersByUsername(String username);
}
