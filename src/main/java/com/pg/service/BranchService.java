package com.pg.service;

import java.lang.constant.Constable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pg.dto.BranchDto;
import com.pg.dto.UserDto;
import com.pg.entity.BranchEntity;
import com.pg.entity.UserEntity;
import com.pg.exception.ResourceNotFoundException;
import com.pg.mapper.BranchMapper;
import com.pg.repository.BranchRepository;
import com.pg.repository.UserRepository;
import com.pg.util.JwtUtil;

@Service
public class BranchService {

	private BranchRepository branchRepository;
	private BranchMapper branchMapper;
	private UserRepository userRepository;
	private JwtUtil jwtUtil;

	public BranchService(BranchRepository branchRepository, BranchMapper branchMapper, UserRepository userRepository,
			JwtUtil jwtUtil) {
		this.branchRepository = branchRepository;
		this.branchMapper = branchMapper;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	public List<BranchDto> getAllBranches(String userId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		java.util.List<BranchEntity> branches = branchRepository.findByUserId(userId);
		return branchMapper.toDtoList(branches);
	}

	public BranchDto getBranchById(String id) {
		Optional<BranchEntity> branch = branchRepository.findById(id);
		if (branch.isEmpty()) {
			throw new ResourceNotFoundException("Branch not found");
		}
		return branchMapper.toDto(branch.get());
	}

	public BranchDto createBranch(BranchDto branchDto, String userId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		branchDto.setUserId(userId);
		BranchEntity branchEntity = branchMapper.toEntity(branchDto);
		branchEntity.setUserId(user);
		BranchEntity branch = branchRepository.save(branchEntity);
		return branchMapper.toDto(branch);
	}

	public BranchDto updateBranch(String branchId, BranchDto branchDto) {
		Optional<BranchEntity> existingBranch = branchRepository.findById(branchId);
		if (existingBranch.isEmpty()) {
			throw new ResourceNotFoundException("Branch not found");
		}
		branchMapper.updateEntityFromDto(branchDto, existingBranch.get());
		if (branchDto.getUserId() != null && !branchDto.getUserId().isBlank()) {
			UserEntity user = userRepository.findById(branchDto.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User not found"));
			existingBranch.get().setUserId(user);
		}
		BranchEntity updatedBranchEntity = branchRepository.save(existingBranch.get());
		return branchMapper.toDto(updatedBranchEntity);
	}

	public void deleteBranch(String branchId) {
		Optional<BranchEntity> existingBranch = branchRepository.findById(branchId);
		if (existingBranch.isEmpty()) {
			throw new ResourceNotFoundException("Branch not found");
		}
		branchRepository.deleteById(branchId);
	}

}
