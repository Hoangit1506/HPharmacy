package com.project.HPharmacy.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "addresses")
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
	
	@Column(name = "address_detail", nullable = false)
    private String addressDetail;
	
	@Column(name = "receiver", nullable = false)
    private String addressReceiver;
	
	@Column(name = "address_phone", nullable = false)
	@Pattern(regexp = "^\\d{10}$", message = "*Phone number must be 10 digits")
    private String addressPhone;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    public Address() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getAddressReceiver() {
		return addressReceiver;
	}

	public void setAddressReceiver(String addressReceiver) {
		this.addressReceiver = addressReceiver;
	}

	public String getAddressPhone() {
		return addressPhone;
	}

	public void setAddressPhone(String addressPhone) {
		this.addressPhone = addressPhone;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
    
}
