package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.repository.CartRepository;
import az.edu.itbrains.karma_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public List<Cart> getCartItemsByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    @Override
    public double calculateSubtotal(List<Cart> cartItems) {
        return cartItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
}
