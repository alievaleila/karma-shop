package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.enums.PaymentMethod;
import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.service.OrderService;
import az.edu.itbrains.karma_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("address") String address,
                           @RequestParam("shippingMethod") String shippingMethod,
                           @RequestParam("paymentMethod") PaymentMethod paymentMethod,
                           Model model) {

        Order order = new Order();
        order.setUser(userService.findByEmail(email));
        order.setBillingAddress(address);
        order.setShippingMethod(shippingMethod);
        order.setPaymentMethod(paymentMethod);
        orderService.createOrder(order);

        model.addAttribute("order", order);
        return "order-summary";
    }
}
