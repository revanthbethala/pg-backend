package com.pg.service;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pg.dto.UserDto;
import com.pg.entity.UserEntity;
import com.pg.exception.DataAlreadyExistsException;
import com.pg.exception.InvalidCredentialException;
import com.pg.mapper.UserMapper;
import com.pg.repository.UserRepository;
import com.pg.util.JwtUtil;


@Service
public class AuthService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private JwtUtil jwtUtil;
	private PasswordEncoder passwordEncoder;
	public AuthService(UserRepository userRepository, UserMapper userMapper,JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.jwtUtil= jwtUtil;
		this.passwordEncoder= passwordEncoder;
	}

	public Map<String, Object> loginService(UserDto userDto) {
		UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
		if(userEntity==null || !passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
			throw new InvalidCredentialException("Invalid credentials");
		}
		 return Map.of(
		          "token", jwtUtil.generateToken(userDto.getEmail()),
		            "user", userMapper.toDto(userEntity)
		        );
	}
	
	public UserDto registerService(UserDto userDto) {
		UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
		if(userEntity!=null) {
			throw new DataAlreadyExistsException("User already exists");
		}
		UserEntity savedUserEntity = userMapper.toEntity(userDto);
		savedUserEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		UserEntity registeredEntity = userRepository.save(savedUserEntity);
		return userMapper.toDto(registeredEntity);
	}
}
