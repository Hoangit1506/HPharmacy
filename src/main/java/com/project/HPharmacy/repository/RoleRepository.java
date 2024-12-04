package com.project.HPharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.HPharmacy.entity.Role;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(String role);
	
	@Query("SELECT r FROM Role r WHERE r.role IN :roleNames")
	Set<Role> findRolesByNameSet(@Param("roleNames") Set<String> roleNames);
}
