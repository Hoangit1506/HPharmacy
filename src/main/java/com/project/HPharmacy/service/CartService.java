package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.Cart;
import com.project.HPharmacy.entity.CartItemKey;

public interface CartService {
    Cart getOrCreateUnconfirmedCart();

    boolean addToCart(Long productId, int quantity);

    boolean updateCartItem(CartItemKey cartItemId, int quantity);

    void removeCartItem(CartItemKey cartItemId);

    void clearCart();

    void save(Cart cart);
}
