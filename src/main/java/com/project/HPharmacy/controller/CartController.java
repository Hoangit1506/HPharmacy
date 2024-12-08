package com.project.HPharmacy.controller;

import com.project.HPharmacy.entity.Cart;
import com.project.HPharmacy.entity.CartItem;
import com.project.HPharmacy.entity.CartItemKey;
import com.project.HPharmacy.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        Cart cart = cartService.getOrCreateUnconfirmedCart();
        model.addAttribute("cart", cart);
        return "cart"; // Trỏ đến trang view giỏ hàng
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        boolean success = cartService.addToCart(productId, quantity);
        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Quantity exceeds stock!");
            return "redirect:/products/" + productId;
        }
        redirectAttributes.addFlashAttribute("message", "Product added to cart!");
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("cartId") Long cartId,
                                 @RequestParam("productId") Long productId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        CartItemKey cartItemKey = new CartItemKey(cartId, productId);
        boolean success = cartService.updateCartItem(cartItemKey, quantity);
        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Quantity exceeds stock or invalid quantity!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cart updated successfully!");
        }
        return "redirect:/cart";
    }

    @PostMapping("/updateAll")
    public String updateAllCartItems(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getOrCreateUnconfirmedCart();
        boolean updateSuccessful = true;
        log.info("{}", allParams);
        // Lặp qua tất cả các sản phẩm trong giỏ hàng và cập nhật số lượng
        for (CartItem cartItem : cart.getCartItems()) {
            String quantityParam = allParams.get("quantity_" + cartItem.getId().getProduct_id());
            if (quantityParam != null) {
                int newQuantity = Integer.parseInt(quantityParam);
                boolean success = cartService.updateCartItem(cartItem.getId(), newQuantity);
                if (!success) {
                    updateSuccessful = false;
                    break;  // Nếu có lỗi thì thoát khỏi vòng lặp
                }
            }
        }

        if (updateSuccessful) {
            redirectAttributes.addFlashAttribute("message", "Cart updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Some quantities were invalid or exceeded stock.");
        }

        return "redirect:/cart";
    }


    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("cartId") Long cartId,
                                 @RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        CartItemKey cartItemKey = new CartItemKey(cartId, productId);
        cartService.removeCartItem(cartItemKey);
        redirectAttributes.addFlashAttribute("message", "Item removed successfully!");
        return "redirect:/cart";
    }

//    @PostMapping("/remove")
//    public String removeCartItem(@RequestParam("cartId") Long cartId,
//                                 @RequestParam("productId") Long productId,
//                                 RedirectAttributes redirectAttributes) {
//        log.info("Remove item from cart: cartId={}, productId={}", cartId, productId);
//        try {
//            CartItemKey cartItemKey = new CartItemKey(cartId, productId);
//            cartService.removeCartItem(cartItemKey);
//            redirectAttributes.addFlashAttribute("message", "Item removed successfully!");
//        } catch (Exception e) {
//            log.error("Error removing item: {}", e.getMessage());
//            redirectAttributes.addFlashAttribute("error", "Failed to remove item.");
//        }
//        return "redirect:/cart";
//    }

    @PostMapping("/removeAll")
    public String removeAllCartItems(RedirectAttributes redirectAttributes) {
        cartService.clearCart();
        redirectAttributes.addFlashAttribute("message", "All items removed successfully!");
        return "redirect:/cart";
    }


}
