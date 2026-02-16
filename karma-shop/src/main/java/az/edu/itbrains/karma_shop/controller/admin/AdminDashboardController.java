package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.model.Contact;
import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.repository.CategoryRepository;
import az.edu.itbrains.karma_shop.repository.ContactRepository;
import az.edu.itbrains.karma_shop.repository.OrderRepository;
import az.edu.itbrains.karma_shop.repository.ProductRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("pageTitle", "Dashboard");

        // Stats
        model.addAttribute("totalProducts", productRepository.count());
        model.addAttribute("totalCategories", categoryRepository.count());
        model.addAttribute("totalOrders", orderRepository.count());
        model.addAttribute("totalUsers", userRepository.count());

        // Recent Orders (last 5)
        List<Order> recentOrders = orderRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
        model.addAttribute("recentOrders", recentOrders);

        // Recent Contacts (last 5)
        List<Contact> recentContacts = contactRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
        model.addAttribute("recentContacts", recentContacts);

        return "admin/dashboard";
    }
}
