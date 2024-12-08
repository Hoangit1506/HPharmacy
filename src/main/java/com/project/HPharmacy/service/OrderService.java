package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.Address;
import com.project.HPharmacy.entity.Cart;
import com.project.HPharmacy.entity.Order;
import com.project.HPharmacy.entity.UserEntity;

public interface OrderService {
    Order createOrder(Cart cart, Address address, UserEntity user);
}
