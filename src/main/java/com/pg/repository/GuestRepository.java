package com.pg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.GuestEntity;
@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, String> {
	

	boolean existsByPhone(String phone);

    boolean existsByAadhaar(String aadhaar);

}