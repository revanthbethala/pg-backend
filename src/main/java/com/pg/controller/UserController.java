package com.pg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.UserDto;
import com.pg.service.UserService;
import com.pg.util.SuccessResponseUtil;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(Authentication authentication) {
        UserDto user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(SuccessResponseUtil.success("Profile fetched successfully", user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteCurrentUser(Authentication authentication) {
        userService.deleteCurrentUser(authentication.getName());
        return ResponseEntity.ok(SuccessResponseUtil.success("Profile deleted successfully"));
    }

}
