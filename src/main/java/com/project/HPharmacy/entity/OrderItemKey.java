package com.project.HPharmacy.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemKey {
	@Column(name = "order_id")
	private Long order_id;
	@Column(name = "product_id")
    private Long product_id;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(order_id, product_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemKey other = (OrderItemKey) obj;
		return Objects.equals(order_id, other.order_id) && Objects.equals(product_id, other.product_id);
	}
	
	
}
