package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);
        double subtotal = cartService.calculateSubtotal(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", subtotal);

        return "cart";
    }

}
