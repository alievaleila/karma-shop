package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);
        double subtotal = cartService.calculateSubtotal(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", subtotal);

        // Get coupon info from session
        String appliedCouponCode = (String) session.getAttribute("appliedCouponCode");
        Double discountAmount = (Double) session.getAttribute("discountAmount");

        if (appliedCouponCode != null && discountAmount != null) {
            model.addAttribute("appliedCouponCode", appliedCouponCode);
            model.addAttribute("discountAmount", discountAmount);
            model.addAttribute("finalTotal", subtotal - discountAmount);
        } else {
            model.addAttribute("finalTotal", subtotal);
        }

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, Principal principal){
        if(principal==null){
            return "redirect:/login";
        }

        String username=principal.getName();
        cartService.addToCart(username,productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, Principal principal){
        if(principal==null){
            return "redirect:/login";
        }

        String username=principal.getName();
        cartService.removeFromCart(username, productId);
        return "redirect:/cart";
    }
}
