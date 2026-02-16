package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.enums.OrderStatus;
import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("currentPage", "orders");
        model.addAttribute("pageTitle", "Sifarişlər");

        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("orders", orders);

        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı"));

        model.addAttribute("currentPage", "orders");
        model.addAttribute("pageTitle", "Sifariş: " + order.getOrderNumber());
        model.addAttribute("order", order);

        return "admin/order-detail";
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, 
                                    @RequestParam("status") String status, 
                                    RedirectAttributes redirectAttributes) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı"));

        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);

        redirectAttributes.addFlashAttribute("successMessage", "Sifariş statusu yeniləndi!");
        return "redirect:/admin/orders";
    }
}
