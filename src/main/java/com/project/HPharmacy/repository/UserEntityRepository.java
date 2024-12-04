package com.project.HPharmacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.HPharmacy.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByPhone(String phone);
}
