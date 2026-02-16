package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.enums.PaymentMethod;
import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.model.OrderItem;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import az.edu.itbrains.karma_shop.service.CartService;
import az.edu.itbrains.karma_shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    private static final Double SHIPPING_COST = 5.00;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Double subtotal = cartService.calculateSubtotal(cartItems);

        String appliedCouponCode = (String) session.getAttribute("appliedCouponCode");
        Double discountAmount = (Double) session.getAttribute("discountAmount");

        if (discountAmount == null) {
            discountAmount = 0.0;
        }

        Double total = subtotal + SHIPPING_COST - discountAmount;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shippingCost", SHIPPING_COST);
        model.addAttribute("discount", discountAmount);
        model.addAttribute("appliedCouponCode", appliedCouponCode);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String company,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String country,
            @RequestParam String addressLine1,
            @RequestParam(required = false) String addressLine2,
            @RequestParam String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String orderNotes,
            @RequestParam(required = false, defaultValue = "false") boolean shipToDifferentAddress,
            @RequestParam(required = false) String shippingFirstName,
            @RequestParam(required = false) String shippingLastName,
            @RequestParam(required = false) String shippingCountry,
            @RequestParam(required = false) String shippingAddressLine1,
            @RequestParam(required = false) String shippingAddressLine2,
            @RequestParam(required = false) String shippingCity,
            @RequestParam(required = false) String shippingDistrict,
            @RequestParam(required = false) String shippingZipCode,
            @RequestParam String paymentMethod,
            @RequestParam(required = false, defaultValue = "false") boolean acceptedTerms,
            Principal principal,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (!acceptedTerms) {
            redirectAttributes.addFlashAttribute("error", "Please accept the terms and conditions");
            return "redirect:/checkout";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);

        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        Double subtotal = cartService.calculateSubtotal(cartItems);

        String appliedCouponCode = (String) session.getAttribute("appliedCouponCode");
        Double discountAmount = (Double) session.getAttribute("discountAmount");

        if (discountAmount == null) {
            discountAmount = 0.0;
        }

        Double total = subtotal + SHIPPING_COST - discountAmount;

        Order order = Order.builder()
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .company(company)
                .phone(phone)
                .email(email)
                .country(country)
                .addressLine1(addressLine1)
                .addressLine2(addressLine2)
                .city(city)
                .district(district)
                .zipCode(zipCode)
                .orderNotes(orderNotes)
                .shipToDifferentAddress(shipToDifferentAddress)
                .shippingFirstName(shipToDifferentAddress ? shippingFirstName : firstName)
                .shippingLastName(shipToDifferentAddress ? shippingLastName : lastName)
                .shippingCountry(shipToDifferentAddress ? shippingCountry : country)
                .shippingAddressLine1(shipToDifferentAddress ? shippingAddressLine1 : addressLine1)
                .shippingAddressLine2(shipToDifferentAddress ? shippingAddressLine2 : addressLine2)
                .shippingCity(shipToDifferentAddress ? shippingCity : city)
                .shippingDistrict(shipToDifferentAddress ? shippingDistrict : district)
                .shippingZipCode(shipToDifferentAddress ? shippingZipCode : zipCode)
                .subtotal(subtotal)
                .shippingCost(SHIPPING_COST)
                .discount(discountAmount)
                .couponCode(appliedCouponCode)
                .total(total)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .acceptedTerms(acceptedTerms)
                .build();

        for (Cart cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .productName(cartItem.getName())
                    .productImage(cartItem.getImageUrl())
                    .price(cartItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            order.addOrderItem(orderItem);
        }

        Order savedOrder = orderService.createOrder(order);


        cartService.clearCart(username);

        session.removeAttribute("appliedCouponCode");
        session.removeAttribute("discountAmount");

        return "redirect:/confirmation/" + savedOrder.getOrderNumber();
    }

    @GetMapping("/confirmation")
    public String confirmationRedirect() {
        return "redirect:/";
    }

    @GetMapping("/confirmation/{orderNumber}")
    public String confirmation(
            @org.springframework.web.bind.annotation.PathVariable String orderNumber,
            Model model,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        Order order = orderService.findByOrderNumber(orderNumber)
                .orElse(null);

        if (order == null) {
            return "redirect:/";
        }

        if (!order.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/";
        }

        model.addAttribute("order", order);
        return "confirmation";
    }
}
