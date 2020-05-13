package com.springboot.example.springbootdemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // spring will initialize this class
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;


    //in constructor injection and when u have multiple impln to an interface
    // its good to specify which implementation you're using here. So @Qualifier is used
    @Autowired
    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao.selectApplicationUsersByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("The username %s not found", username))
                );
    }
}
