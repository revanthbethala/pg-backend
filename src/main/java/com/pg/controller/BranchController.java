package com.pg.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.BranchDto;
import com.pg.dto.RoomDto;
import com.pg.service.BranchService;
import com.pg.service.RoomService;
import com.pg.util.SuccessResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/branches")
public class BranchController {

	private BranchService branchService;
	private RoomService roomService;

	public BranchController(BranchService branchService, RoomService roomService) {
		this.branchService = branchService;
		this.roomService = roomService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BranchDto>> getBranchById(@PathVariable String id) {
		BranchDto branchDto = branchService.getBranchById(id);
		return ResponseEntity.ok(SuccessResponseUtil.success("Branch fetched successfully", branchDto));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<BranchDto>>> getAllBranches(
			Authentication authentication) {
		List<BranchDto> branches = branchService.getAllBranches(authentication.getName());
		return ResponseEntity.ok(SuccessResponseUtil.success("Branches fetched successfully", branches));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<BranchDto>> createBranch(
			Authentication authentication,
			@Valid @RequestBody BranchDto branchDto) {
		BranchDto dto = branchService.createBranch(branchDto, authentication.getName());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessResponseUtil.success("Branch created successfully", dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BranchDto>> updateBranch(
			@PathVariable String id,
			@Valid @RequestBody BranchDto branchDto) {

		BranchDto dto = branchService.updateBranch(id, branchDto);

		return ResponseEntity.ok(
				SuccessResponseUtil.success("Branch updated successfully", dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteBranch(
			@PathVariable String id) {

		branchService.deleteBranch(id);

		return ResponseEntity.ok(
				SuccessResponseUtil.success("Branch deleted successfully"));
	}

	@GetMapping("/{branchId}/rooms")
	public ResponseEntity<ApiResponse<List<RoomDto>>> getAllRooms(@PathVariable String branchId) {

		List<RoomDto> rooms = roomService.getAllRooms(branchId);

		return ResponseEntity.ok(
				SuccessResponseUtil.success("Rooms fetched successfully", rooms));
	}

	@PostMapping("/{branchId}/rooms")
	public ResponseEntity<ApiResponse<RoomDto>> createRoom(
			@PathVariable String branchId,
			@Valid @RequestBody RoomDto roomDto) {

		RoomDto dto = roomService.createRoom(branchId, roomDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessResponseUtil.success(
						"Room created successfully", dto));
	}

}
