package com.pg.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.UserDto;
import com.pg.service.AuthService;
import com.pg.util.SuccessResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	private AuthService authService;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping("/hello")
	public ResponseEntity<ApiResponse<String>> sayHello() {
		return ResponseEntity.ok(SuccessResponseUtil.success("Hello fetched successfully", "hello"));
	}


	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Map<String, Object>>> loginController(@Valid @RequestBody UserDto userDto) {
		Map<String, Object> authResponse = authService.loginService(userDto);
		return ResponseEntity.ok(SuccessResponseUtil.success("Login successful", authResponse));
	}
	

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDto>> registerController(@Valid @RequestBody UserDto userDto) {
		UserDto user = authService.registerService(userDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessResponseUtil.success("User registered successfully", user));

	}
}
