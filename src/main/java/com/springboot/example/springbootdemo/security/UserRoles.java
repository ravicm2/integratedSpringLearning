package com.springboot.example.springbootdemo.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.springboot.example.springbootdemo.security.UserPermissions.*;

public enum UserRoles {

    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, CONTENT_READ, CONTENT_WRITE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }
}
