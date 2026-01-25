package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TrackingController {

    private final OrderService orderService;

    @GetMapping("/tracking")
    public String showTrackingForm() {
        return "tracking";
    }

    @PostMapping("/tracking")
    public String trackOrder(
            @RequestParam String orderNumber,
            @RequestParam String email,
            RedirectAttributes redirectAttributes
    ) {
        if (orderNumber == null || orderNumber.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter order number");
            return "redirect:/tracking";
        }

        if (email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter email address");
            return "redirect:/tracking";
        }

        Optional<Order> orderOpt = orderService.findByOrderNumberAndEmail(orderNumber.trim(), email.trim());

        if (orderOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Order not found. Please check your order number and email.");
            redirectAttributes.addFlashAttribute("orderNumber", orderNumber);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/tracking";
        }

        redirectAttributes.addFlashAttribute("order", orderOpt.get());
        redirectAttributes.addFlashAttribute("orderFound", true);
        return "redirect:/tracking";
    }
}
