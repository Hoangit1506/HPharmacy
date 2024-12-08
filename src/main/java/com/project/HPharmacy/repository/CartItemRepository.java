package com.project.HPharmacy.repository;

import com.project.HPharmacy.entity.CartItem;
import com.project.HPharmacy.entity.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {

}
