package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.repository.OrderRepository;
import az.edu.itbrains.karma_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Optional<Order> findByOrderNumberAndEmail(String orderNumber, String email) {
        return orderRepository.findByOrderNumberAndEmail(orderNumber, email);
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUserUsernameOrderByCreatedAtDesc(username);
    }
}
