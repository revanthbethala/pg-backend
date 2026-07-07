package com.pg.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pg.entity.UserEntity;
import com.pg.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        		if(user==null) {
        		throw new UsernameNotFoundException("User not found with email");
        		}
        // Return a Spring Security User object mapped to your database user
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // This acts as the standard "username" under the hood
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
