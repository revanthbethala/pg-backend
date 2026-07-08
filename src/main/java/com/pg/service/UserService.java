package com.pg.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pg.dto.UserDto;
import com.pg.entity.UserEntity;
import com.pg.exception.ResourceNotFoundException;
import com.pg.mapper.UserMapper;
import com.pg.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getCurrentUser(String userId) {

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw  new ResourceNotFoundException("User not found");

        }
        UserDto dto = userMapper.toDto(user.get());

        // Don't expose password
        dto.setPassword(null);

        return dto;
    }

    public void deleteCurrentUser(String userId) {

    	 Optional<UserEntity> user = userRepository.findById(userId);
         if(user.isEmpty()) {
             throw  new ResourceNotFoundException("User not found");

         }        userRepository.deleteById(userId);
    }
}