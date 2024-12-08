package com.project.HPharmacy.controller;

import com.project.HPharmacy.entity.Address;
import com.project.HPharmacy.entity.UserEntity;
import com.project.HPharmacy.service.AddressService;
import com.project.HPharmacy.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping
    public String listAddresses(Model model) {
        UserEntity currentUser = userEntityService.getCurrentUser();
        List<Address> addresses = addressService.getAddressesByUserId(currentUser.getId());
        model.addAttribute("addresses", addresses);
        return "addresses/list"; // Giao diện danh sách địa chỉ
    }

    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());
        return "addresses/add-edit-address"; // Giao diện thêm địa chỉ
    }

    @PostMapping("/add")
    public String saveAddress(@ModelAttribute("address") Address address) {
        UserEntity currentUser = userEntityService.getCurrentUser();
        address.setUser(currentUser);
        addressService.saveAddress(address);
        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
    public String editAddressForm(@PathVariable("id") Long id, Model model) {
        Address address = addressService.getAddressById(id);
        model.addAttribute("address", address);
        model.addAttribute("isEdit", true); // Thêm biến để biết là chỉnh sửa
        return "addresses/add-edit-address"; // Giao diện chỉnh sửa địa chỉ
    }

    @PostMapping("/edit/{id}")
    public String updateAddress(@PathVariable("id") Long id, @ModelAttribute("address") Address updatedAddress) {
        Address address = addressService.getAddressById(id);
        if (address != null) {
            address.setAddressDetail(updatedAddress.getAddressDetail());
            address.setAddressReceiver(updatedAddress.getAddressReceiver());
            address.setAddressPhone(updatedAddress.getAddressPhone());
            addressService.saveAddress(address);
        }
        return "redirect:/addresses";
    }

    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }
}

