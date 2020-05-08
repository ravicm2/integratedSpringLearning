package com.springboot.example.springbootdemo.security;

public enum UserPermissions {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    CONTENT_READ("content:read"),
    CONTENT_WRITE("content:write");

    private final String permissions;

    UserPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}
