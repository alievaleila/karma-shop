package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.model.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getCartItemsByUsername(String username);

    double calculateSubtotal(List<Cart> cartItems);

    void addToCart(String username, Long productId);

    void removeFromCart(String username, Long productId);

    void clearCart(String username);
}
