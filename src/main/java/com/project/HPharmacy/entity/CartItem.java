package com.project.HPharmacy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("cart_id")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "item_quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
    private Integer quantity;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.00", message = "*Price has to be non negative number")
    @Digits(integer = 10, fraction = 2, message = "*Price format invalid")
    private BigDecimal price;

    public CartItem() {

    }

    public CartItem(Product product, Integer quantity) {
        this.quantity = quantity;
        this.product = product;
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

    public @DecimalMin(value = "0.00", message = "*Price has to be non negative number") @Digits(integer = 10, fraction = 2, message = "*Price format invalid") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@DecimalMin(value = "0.00", message = "*Price has to be non negative number") @Digits(integer = 10, fraction = 2, message = "*Price format invalid") BigDecimal price) {
        this.price = price;
    }
}
