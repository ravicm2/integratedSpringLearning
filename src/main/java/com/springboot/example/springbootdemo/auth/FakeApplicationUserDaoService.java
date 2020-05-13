package com.springboot.example.springbootdemo.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.springboot.example.springbootdemo.security.UserRoles.ADMIN;
import static com.springboot.example.springbootdemo.security.UserRoles.ADMINTRAINEE;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUsers> selectApplicationUsersByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<ApplicationUsers> getApplicationUsers() {
        List<ApplicationUsers> applicationUsers = Lists.newArrayList(
                new ApplicationUsers(ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("admin"),
                        "admin",
                        true,
                        true,
                        true,
                        true),
                new ApplicationUsers(ADMINTRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("trainee"),
                        "trainee",
                        true,
                        true,
                        true,
                        true)

        );
        return applicationUsers;
    }
}
