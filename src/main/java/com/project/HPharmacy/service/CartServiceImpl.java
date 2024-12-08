package com.project.HPharmacy.service;

import com.project.HPharmacy.entity.*;
import com.project.HPharmacy.repository.CartItemRepository;
import com.project.HPharmacy.repository.CartRepository;
import com.project.HPharmacy.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public Cart getOrCreateUnconfirmedCart() {
        // Lấy User hiện tại
        UserEntity currentUser = userEntityService.getCurrentUser();

        // Kiểm tra nếu đã có Cart UNCONFIRMED
        Optional<Cart> existingCart = cartRepository.findByUserAndStatus(currentUser, CartStatus.UNCONFIRMED);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        // Tạo mới Cart nếu chưa tồn tại
        Cart newCart = new Cart();
        newCart.setUser(currentUser);
        newCart.setStatus(CartStatus.UNCONFIRMED);
        newCart.setTotalPrice(BigDecimal.ZERO); // Giá trị mặc định ban đầu
        return cartRepository.save(newCart);
    }

    private void updateCartTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    @Override
    public boolean addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || product.getQuantity() < quantity) {
            return false; // Không đủ hàng
        }

        // Lấy hoặc tạo giỏ hàng
        Cart cart = getOrCreateUnconfirmedCart();

        // Kiểm tra quyền sở hữu giỏ hàng
        if (!cart.getUser().getId().equals(userEntityService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Tăng số lượng nếu sản phẩm đã tồn tại
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Thêm sản phẩm mới
            CartItem newCartItem = new CartItem();
            CartItemKey cartItemKey = new CartItemKey(cart.getId(), productId);
            newCartItem.setId(cartItemKey);
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setPrice(product.getPrice());
            cart.getCartItems().add(newCartItem);
        }

        updateCartTotalPrice(cart);
        return true;
    }

    @Override
    public boolean updateCartItem(CartItemKey cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        // Kiểm tra quyền sở hữu giỏ hàng
        if (!cartItem.getCart().getUser().getId().equals(userEntityService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        if (quantity <= 0) {
            // Xóa sản phẩm nếu số lượng = 0
            removeCartItem(cartItemId);
            return true;
        }

        if (quantity > cartItem.getProduct().getQuantity()) {
            return false; // Số lượng vượt quá tồn kho
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        updateCartTotalPrice(cartItem.getCart());
        return true;
    }


    @Override
    public void removeCartItem(CartItemKey cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        // Kiểm tra quyền sở hữu giỏ hàng
        if (!cartItem.getCart().getUser().getId().equals(userEntityService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        // Cập nhật danh sách giỏ hàng trong Cart
        Cart cart = cartItem.getCart();
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));

        // Xóa sản phẩm khỏi giỏ hàng qua repository
        cartItemRepository.delete(cartItem);

        // Cập nhật tổng giá trị
        updateCartTotalPrice(cart);
//        cartRepository.save(cart);
    }

//    @Override
//    public void removeCartItem(CartItemKey cartItemId) {
//        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
//        if (optionalCartItem.isEmpty()) {
//            throw new RuntimeException("CartItem not found");
//        }
//        CartItem cartItem = optionalCartItem.get();
//
//        // Kiểm tra quyền sở hữu giỏ hàng
//        if (!cartItem.getCart().getUser().getId().equals(userEntityService.getCurrentUser().getId())) {
//            throw new RuntimeException("Unauthorized access to cart");
//        }
//
//        // Xóa sản phẩm khỏi giỏ hàng
//        Cart cart = cartItem.getCart();
//        cartItemRepository.deleteById(cartItemId); // Xóa qua repository
//        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
//
//        // Xóa giỏ hàng nếu không còn sản phẩm nào
//        if (cart.getCartItems().isEmpty()) {
//            cartRepository.delete(cart);
//        } else {
//            updateCartTotalPrice(cart);
//            cartRepository.save(cart);
//        }
//    }

    @Override
    public void clearCart() {
        Cart cart = getOrCreateUnconfirmedCart();

        // Kiểm tra quyền sở hữu giỏ hàng
        if (!cart.getUser().getId().equals(userEntityService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }
        cart.getCartItems().clear();

        // Đặt tổng giá trị giỏ hàng về 0
        updateCartTotalPrice(cart);
        // Xóa toàn bộ sản phẩm trong giỏ hàng
        cartItemRepository.deleteAll(cart.getCartItems());
        cartRepository.save(cart);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
