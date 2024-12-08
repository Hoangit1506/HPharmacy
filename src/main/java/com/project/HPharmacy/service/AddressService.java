package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.Address;
import com.project.HPharmacy.entity.UserEntity;

import java.util.List;

public interface AddressService {
    List<Address> getAddressesByUserId(Long userId);

    Address saveAddress(Address address);

    void deleteAddress(Long addressId);

    Address getAddressById(Long addressId);

    Address findByIdAndUser(Long addressId, UserEntity user);
}
