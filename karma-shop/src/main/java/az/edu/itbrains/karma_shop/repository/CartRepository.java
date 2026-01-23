package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.model.Product;
import az.edu.itbrains.karma_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    

    Cart findByUserAndProduct(User user, Product product);

    List<Cart> findByUserUsername(String username);
}
