package com.pg.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.AuthResponseDto;
import com.pg.dto.LoginRequestDto;
import com.pg.dto.RefreshTokenRequestDto;
import com.pg.dto.RegisterRequestDto;
import com.pg.dto.UserDto;
import com.pg.service.AuthService;
import com.pg.util.SuccessResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponseDto>> loginController(
			@Valid @RequestBody LoginRequestDto loginDto) {

		AuthResponseDto authResponse = authService.loginService(loginDto);

		return ResponseEntity.ok(
				SuccessResponseUtil.success(
						"Login successful",
						authResponse));
	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<AuthResponseDto>> refreshTokenController(
			@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {

		AuthResponseDto authResponse = authService.refreshToken(refreshTokenRequestDto);

		return ResponseEntity.ok(
				SuccessResponseUtil.success(
						"Token refreshed successfully",
						authResponse));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logoutController(
			@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {

		authService.logout(refreshTokenRequestDto);

		return ResponseEntity.ok(
				SuccessResponseUtil.success(
						"Logged out successfully"));
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDto>> registerController(
			@Valid @RequestBody RegisterRequestDto userDto) {

		UserDto user = authService.registerService(userDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(
						SuccessResponseUtil.success(
								"User registered successfully",
								user));
	}
}