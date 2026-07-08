package com.pg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pg.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	public UserEntity findByEmail(String email);

	public UserEntity findByRefreshToken(String refreshToken);
}
