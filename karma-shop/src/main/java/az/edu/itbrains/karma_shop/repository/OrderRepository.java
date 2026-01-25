package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByOrderNumberAndEmail(String orderNumber, String email);

    List<Order> findByUserUsernameOrderByCreatedAtDesc(String username);
}
