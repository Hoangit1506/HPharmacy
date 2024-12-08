package com.project.HPharmacy.controller;

import com.project.HPharmacy.entity.Address;
import com.project.HPharmacy.entity.Cart;
import com.project.HPharmacy.entity.CartStatus;
import com.project.HPharmacy.entity.UserEntity;
import com.project.HPharmacy.service.AddressService;
import com.project.HPharmacy.service.CartService;
import com.project.HPharmacy.service.OrderService;
import com.project.HPharmacy.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    UserEntityService userEntityService;

    @Autowired
    CartService cartService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderService orderService;

    @GetMapping
    public String checkoutPage(Model model) {
        UserEntity user = userEntityService.getCurrentUser();
        Cart cart = cartService.getOrCreateUnconfirmedCart();

        if (cart == null || cart.getCartItems().isEmpty()) {
            model.addAttribute("error", "Your cart is empty. Please add products before checkout.");
            return "redirect:/cart";
        }

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("addresses", addressService.getAddressesByUserId(user.getId()));
        return "checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam("addressId") Long addressId, Model model) {
        UserEntity user = userEntityService.getCurrentUser();
        Cart cart = cartService.getOrCreateUnconfirmedCart();

        // Kiểm tra giỏ hàng rỗng
        if (cart == null || cart.getCartItems().isEmpty()) {
            model.addAttribute("error", "Your cart is empty. Please add products before checkout.");
            return "redirect:/cart";
        }

        // Kiểm tra trạng thái giỏ hàng
        if (cart.getStatus() != CartStatus.UNCONFIRMED) {
            model.addAttribute("error", "Invalid cart status. Please create a new cart to place an order.");
            return "redirect:/cart";
        }

        // Kiểm tra địa chỉ hợp lệ
        Address address = addressService.findByIdAndUser(addressId, user);
        if (address == null) {
            model.addAttribute("error", "Address not found or does not belong to the current user.");
            return "redirect:/checkout";
        }

        try {
            // Tạo đơn hàng
            orderService.createOrder(cart, address, user);

            // Cập nhật trạng thái giỏ hàng
            cart.setStatus(CartStatus.CONFIRMED);
            cartService.save(cart);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while placing the order. Please try again.");
            return "checkout";
        }

        return "redirect:/thankyou";
    }


}
