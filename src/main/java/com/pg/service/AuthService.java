package com.pg.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pg.dto.AuthResponseDto;
import com.pg.dto.LoginRequestDto;
import com.pg.dto.RefreshTokenRequestDto;
import com.pg.dto.RegisterRequestDto;
import com.pg.dto.UserDto;
import com.pg.entity.UserEntity;
import com.pg.exception.DataAlreadyExistsException;
import com.pg.exception.InvalidCredentialException;
import com.pg.exception.ResourceNotFoundException;
import com.pg.mapper.UserMapper;
import com.pg.repository.UserRepository;
import com.pg.util.JwtUtil;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	public AuthService(
			UserRepository userRepository,
			UserMapper userMapper,
			JwtUtil jwtUtil,
			PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	public AuthResponseDto loginService(LoginRequestDto userDto) {

		UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());

		if (userEntity == null ||
				!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
			throw new InvalidCredentialException("Invalid credentials");
		}

		String accessToken = jwtUtil.generateAccessToken(userEntity.getId());
		String refreshToken = jwtUtil.generateRefreshToken(userEntity.getId());

		userEntity.setRefreshToken(refreshToken);
		userRepository.save(userEntity);

		return new AuthResponseDto(
				accessToken,
				refreshToken,
				userMapper.toDto(userEntity));
	}

	public AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto) {

		String refreshToken = requestDto.getRefreshToken();

		UserEntity userEntity = userRepository.findByRefreshToken(refreshToken);

		if (userEntity == null ||
				!jwtUtil.validateRefreshToken(refreshToken, userEntity.getId())) {
			throw new InvalidCredentialException("Invalid refresh token");
		}

		String newAccessToken = jwtUtil.generateAccessToken(userEntity.getId());
		String newRefreshToken = jwtUtil.generateRefreshToken(userEntity.getId());

		userEntity.setRefreshToken(newRefreshToken);
		userRepository.save(userEntity);

		return new AuthResponseDto(
				newAccessToken,
				newRefreshToken,
				userMapper.toDto(userEntity));
	}

	public void logout(RefreshTokenRequestDto requestDto) {

		String refreshToken = requestDto.getRefreshToken();

		UserEntity userEntity = userRepository.findByRefreshToken(refreshToken);

		if (userEntity == null) {
			throw new ResourceNotFoundException("Refresh token not found");
		}

		userEntity.setRefreshToken(null);
		userRepository.save(userEntity);
	}

	public UserDto registerService(RegisterRequestDto userDto) {

		UserEntity existingUser = userRepository.findByEmail(userDto.getEmail());

		if (existingUser != null) {
			throw new DataAlreadyExistsException("User already exists");
		}

		UserEntity userEntity = userMapper.toEntity(userDto);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

		UserEntity savedUser = userRepository.save(userEntity);

		return userMapper.toDto(savedUser);
	}
}