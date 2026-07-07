package com.pg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.BranchEntity;
import com.pg.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

	public boolean existsByRoomNumber(int roomNumber);

	public boolean existsByRoomNumberAndIdNot(int roomNumber, String id);
    List<RoomEntity> findByBranchId(BranchEntity branch);

}
