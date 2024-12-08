package com.project.HPharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemKey implements Serializable {
    @Column(name = "cart_id")
    private Long cart_id;
    @Column(name = "product_id")
    private Long product_id;

    public CartItemKey() {
    }

    public CartItemKey(Long cart_id, Long product_id) {
        this.cart_id = cart_id;
        this.product_id = product_id;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart_id, product_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItemKey other = (CartItemKey) obj;
        return Objects.equals(cart_id, other.cart_id) && Objects.equals(product_id, other.product_id);
    }
}
