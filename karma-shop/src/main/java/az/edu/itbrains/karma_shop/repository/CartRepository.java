package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUsername(String username);
}
