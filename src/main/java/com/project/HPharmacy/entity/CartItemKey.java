package com.project.HPharmacy.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CartItemKey {
	@Column(name = "cart_id")
	private Long cart_id;
	@Column(name = "product_id")
    private Long product_id;
	
	
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
