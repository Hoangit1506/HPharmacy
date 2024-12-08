package com.project.HPharmacy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Enumerated(EnumType.STRING) // Lưu dạng chuỗi (e.g., "PENDING", "CONFIRMED")
    @Column(name = "status", nullable = false)
    private CartStatus status = CartStatus.UNCONFIRMED;

    @Column(name = "total_price", nullable = true)
    @DecimalMin(value = "0.00", message = "*Total price has to be non negative number")
    @Digits(integer = 10, fraction = 2, message = "*Total price format invalid")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Order order;

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public @DecimalMin(value = "0.00", message = "*Total price has to be non negative number") @Digits(integer = 10, fraction = 2, message = "*Total price format invalid") BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@DecimalMin(value = "0.00", message = "*Total price has to be non negative number") @Digits(integer = 10, fraction = 2, message = "*Total price format invalid") BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
