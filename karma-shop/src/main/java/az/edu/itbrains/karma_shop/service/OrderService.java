package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> getOrdersByUsername(String username);
}
