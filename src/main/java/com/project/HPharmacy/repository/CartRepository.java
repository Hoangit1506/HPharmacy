package com.project.HPharmacy.repository;

import com.project.HPharmacy.entity.Cart;
import com.project.HPharmacy.entity.CartStatus;
import com.project.HPharmacy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndStatus(UserEntity user, CartStatus status);
}
