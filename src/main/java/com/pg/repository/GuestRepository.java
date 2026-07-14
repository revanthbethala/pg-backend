package com.pg.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.GuestEntity;
@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, String> {
	
	List<GuestEntity> findByRoom_id(String roomId);

	boolean existsByPhone(String phone);

    boolean existsByAadhaar(String aadhaar);

}