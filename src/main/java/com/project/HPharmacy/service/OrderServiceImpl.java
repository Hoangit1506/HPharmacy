package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.*;
import com.project.HPharmacy.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Cart cart, Address address, UserEntity user) {
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address is required.");
        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setCart(cart);
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setLastModifiedDate(LocalDateTime.now());

        cart.setStatus(CartStatus.CONFIRMED);

        // Lưu đơn hàng
        return orderRepository.save(order);
    }
}
