package com.pg.controller;

import com.pg.service.BranchService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pg.dto.ApiResponse;
import com.pg.dto.BranchDto;
import com.pg.dto.UserDto;
import com.pg.service.UserService;
import com.pg.util.SuccessResponseUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final BranchService branchService;
	private final UserService userService;

    public UserController(UserService userService, BranchService branchService) {
        this.userService = userService;
        this.branchService = branchService; 
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
    
	@GetMapping("/{userId}/branches")
	public ResponseEntity<ApiResponse<List<BranchDto>>> getAllBranches(@PathVariable String userId) {
	    List<BranchDto> branches = branchService.getAllBranches(userId);
	    return ResponseEntity.ok(SuccessResponseUtil.success("Branches fetched successfully", branches));
	}

	@PostMapping("/{userId}/branches")
	public ResponseEntity<ApiResponse<BranchDto>> createBranch(@PathVariable String userId, @Valid @RequestBody BranchDto branchDto) {
		BranchDto dto = branchService.createBranch(branchDto,userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponseUtil.success("Branch created successfully",dto));
	}
}
