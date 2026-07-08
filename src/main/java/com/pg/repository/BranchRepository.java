package com.pg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.BranchEntity;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, String> {
	public BranchEntity findByBranchName(String branchName);

	public List<BranchEntity> findByUser_Id(String userId);

}
