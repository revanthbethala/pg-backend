package com.pg.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.UserDto;
import com.pg.service.AuthService;
import com.pg.util.ErrorResponseUtil;
import com.pg.util.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	private AuthService authService;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}


	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginController(@Valid @RequestBody UserDto userDto,BindingResult result ) {
		if(result.hasErrors()) {
			Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

			if (!validationErrors.isEmpty()) {
				Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
				error.put("error", validationErrors);
				return ResponseEntity.badRequest().body(error);
			}
		}
		Map<String, Object> authResponse = authService.loginService(userDto);
		return ResponseEntity.ok(Map.of("message", "Login successful", "token", authResponse.get("token"),"user",authResponse.get("user")));
	}
	

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerController(@Valid @RequestBody UserDto userDto,BindingResult result ) {
		if(result.hasErrors()) {
			Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

			if (!validationErrors.isEmpty()) {
				Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
				error.put("error", validationErrors);
				return ResponseEntity.badRequest().body(error);
			}
		}
		UserDto user = authService.registerService(userDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "User registered successfully", "data", user));

	}
}
