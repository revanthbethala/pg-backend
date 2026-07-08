package com.pg.service;

import java.util.Collections;
import java.util.Optional;

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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email");
        }
        UserEntity user = userEntity.get();
        // Return a Spring Security User object mapped to your database user
        return new org.springframework.security.core.userdetails.User(
                user.getId(), // This acts as the standard "username" under the hood
                user.getPassword(),
                Collections.emptyList());
    }
}
