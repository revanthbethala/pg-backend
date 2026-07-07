package com.pg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.BranchDto;
import com.pg.dto.RoomDto;
import com.pg.service.BranchService;
import com.pg.service.RoomService;
import com.pg.util.ErrorResponseUtil;
import com.pg.util.SuccessResponseUtil;
import com.pg.util.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/branches")
public class BranchController {

	private BranchService branchService;
	private RoomService roomService;
	
	public BranchController(BranchService branchService,RoomService roomService) {
		this.branchService = branchService;
		this.roomService = roomService;
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getBranchById(@PathVariable String id) {
		BranchDto branchDto = branchService.getBranchById(id);
		return ResponseEntity.ok(SuccessResponseUtil.success("Branch found",branchDto));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateBranch(
	        @PathVariable String id,
	        @Valid @RequestBody BranchDto branchDto,
	        BindingResult result) {

	    if (result.hasErrors()) {
	        Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

	        if (!validationErrors.isEmpty()) {
	            Map<String, Object> error =
	                    ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
	            error.put("errors", validationErrors);
	            return ResponseEntity.badRequest().body(error);
	        }
	    }

	    BranchDto dto = branchService.updateBranch(id, branchDto);

	    return ResponseEntity.ok(
	            SuccessResponseUtil.success("Branch updated successfully", dto)
	    );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteBranch(
	        @PathVariable String id) {

	    branchService.deleteBranch(id);

	    return ResponseEntity.ok(
	            SuccessResponseUtil.success("Branch deleted successfully")
	    );
	}

	   @GetMapping("/{branchId}/rooms")
	    public ResponseEntity<Map<String, Object>> getAllRooms(@PathVariable String branchId) {

	        List<RoomDto> rooms = roomService.getAllRooms(branchId);

	        return ResponseEntity.ok(
	                SuccessResponseUtil.success("Rooms fetched successfully", rooms));
	    }

	 
	    @PostMapping("/{branchId}/rooms")
	    public ResponseEntity<Map<String, Object>> createRoom(
	            @PathVariable String branchId,
	            @Valid @RequestBody RoomDto roomDto,
	            BindingResult result) {

	        if (result.hasErrors()) {

	            Map<String, String> validationErrors =
	                    ValidationUtil.getValidationErrors(result);

	            if (!validationErrors.isEmpty()) {

	                Map<String, Object> error =
	                        ErrorResponseUtil.buildError(
	                                HttpStatus.BAD_REQUEST,
	                                "Validation failed");

	                error.put("errors", validationErrors);

	                return ResponseEntity.badRequest().body(error);
	            }
	        }

	        RoomDto dto = roomService.createRoom(branchId, roomDto);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(SuccessResponseUtil.success(
	                        "Room created successfully", dto));
	    }
	

}
