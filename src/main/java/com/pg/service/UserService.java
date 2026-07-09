package com.pg.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pg.dto.UserDto;
import com.pg.entity.UserEntity;
import com.pg.exception.DataAlreadyExistsException;
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

        dto.setPassword(null);

        return dto;
    }

    public void deleteCurrentUser(String userId) {

    	 Optional<UserEntity> user = userRepository.findById(userId);
         if(user.isEmpty()) {
             throw  new ResourceNotFoundException("User not found");

         }        userRepository.deleteById(userId);
    }
    
    public UserDto updateCurrentUser(String userId,UserDto dto) {

   	 Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()) {
            throw  new ResourceNotFoundException("User not found");
        }      
        UserEntity user = userEntity.get();
        if(!dto.getEmail().equals(user.getEmail()) && userRepository.findByEmail(dto.getEmail())!=null) {
        	throw new DataAlreadyExistsException("Email already exists");
       	
        }
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        userRepository.save(user);
        return userMapper.toDto(user);
   }

}