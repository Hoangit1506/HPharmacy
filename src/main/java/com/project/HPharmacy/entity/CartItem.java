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
@Table(name = "cart_items")
public class CartItem {
	@EmbeddedId
	private CartItemKey id;
	
	@ManyToOne
    @MapsId("cart_id")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;
	
	@Column(name = "item_quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
	private Integer quantity;
	
	public CartItem() {
		
	}

	public CartItemKey getId() {
		return id;
	}

	public void setId(CartItemKey id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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
