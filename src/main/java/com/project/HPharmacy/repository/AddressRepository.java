package com.project.HPharmacy.repository;

import com.project.HPharmacy.entity.Address;
import com.project.HPharmacy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId); // Lấy danh sách địa chỉ theo userId

    Address findByIdAndUser(Long addressId, UserEntity user);


}