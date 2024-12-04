package com.project.HPharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@EmbeddedId
	private OrderItemKey id;
	
	@ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;
	
	@Column(name = "item_quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
	private Integer quantity;
	
	public OrderItem() {
		
	}

	public OrderItemKey getId() {
		return id;
	}

	public void setId(OrderItemKey id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
