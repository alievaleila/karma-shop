package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.model.Product;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.CartRepository;
import az.edu.itbrains.karma_shop.repository.ProductRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import az.edu.itbrains.karma_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Cart> getCartItemsByUsername(String username) {
        return cartRepository.findByUserUsername(username);
    }

    @Override
    public double calculateSubtotal(List<Cart> cartItems) {
        return cartItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }

    @Override
    public void addToCart(String username, Long productId) {
        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow();
        Cart existing = cartRepository.findByUserAndProduct(user, product);
        if (existing != null) {
            System.out.println("Increased");
            existing.setQuantity(existing.getQuantity() + 1);
            cartRepository.save(existing);
        } else {
            System.out.println("New cart was added.");
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setQuantity(1);
            newCart.setName(product.getName());
            newCart.setImageUrl(product.getImageUrl());
            newCart.setPrice(product.getPrice());
            cartRepository.save(newCart);
        }
    }

    @Override
    public void removeFromCart(String username, Long productId) {
        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow();
        Cart cart = cartRepository.findByUserAndProduct(user, product);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }
}

