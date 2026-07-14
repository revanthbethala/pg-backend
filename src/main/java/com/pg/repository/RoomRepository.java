package com.pg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.BranchEntity;
import com.pg.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

	boolean existsByRoomNumberAndBranchId(String roomNumber, String branchId);

	public boolean existsByRoomNumberAndIdNotAndBranchId(String roomNumber, String id,String branchId);
    List<RoomEntity> findByBranch_Id(String branchId);

}
