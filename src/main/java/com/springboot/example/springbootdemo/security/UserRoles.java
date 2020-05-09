package com.springboot.example.springbootdemo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.springboot.example.springbootdemo.security.UserPermissions.*;

public enum UserRoles {

    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, CONTENT_READ, CONTENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(USER_READ, CONTENT_READ));

    private final Set<UserPermissions> permissions;

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(userPermissions -> new SimpleGrantedAuthority(userPermissions.name()))
                .collect(Collectors.toSet());

        //setting the role and permission manually instead of roles method in UserDetails.
        //this keyword represents the role
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
