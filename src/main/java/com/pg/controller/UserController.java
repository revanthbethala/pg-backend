package com.pg.controller;

import com.pg.service.BranchService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pg.dto.BranchDto;
import com.pg.dto.UserDto;
import com.pg.service.UserService;
import com.pg.util.ErrorResponseUtil;
import com.pg.util.SuccessResponseUtil;
import com.pg.util.ValidationUtil;
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
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        UserDto user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(SuccessResponseUtil.success("Profile fetched successfully", user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Map<String, Object>> deleteCurrentUser(Authentication authentication) {
        userService.deleteCurrentUser(authentication.getName());
        return ResponseEntity.ok(SuccessResponseUtil.success("Profile deleted successfully"));
    }
    
	@GetMapping("/{userId}/branches")
	public ResponseEntity<Map<String, Object>> getAllBranches(@PathVariable String userId) {
	    List<BranchDto> branches = branchService.getAllBranches(userId);
	    return ResponseEntity.ok(SuccessResponseUtil.success("Branches fetched successfully", branches));
	}

	@PostMapping("/{userId}/branches")
	public ResponseEntity<Map<String, Object>> createBranch(@PathVariable String userId, @Valid @RequestBody BranchDto branchDto,BindingResult result) {
		if(result.hasErrors()) {
		Map<String, String> validationErrors = 	ValidationUtil.getValidationErrors(result);
		if (!validationErrors.isEmpty()) {
				Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
				error.put("error", validationErrors);
				return ResponseEntity.badRequest().body(error);
			}
		}
		BranchDto dto = branchService.createBranch(branchDto,userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponseUtil.success("Branch created",dto));
	}
}