package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.model.Cart;
import az.edu.itbrains.karma_shop.model.Coupon;
import az.edu.itbrains.karma_shop.service.CartService;
import az.edu.itbrains.karma_shop.service.CouponService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final CartService cartService;

    @PostMapping("/coupon/apply")
    public String applyCoupon(
            @RequestParam String code,
            Principal principal,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("couponError", "Please login to apply coupon");
            return "redirect:/login";
        }

        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);
        Double cartTotal = cartService.calculateSubtotal(cartItems);

        if (cartTotal <= 0) {
            redirectAttributes.addFlashAttribute("couponError", "Your cart is empty");
            return "redirect:/cart";
        }

        Optional<Coupon> couponOpt = couponService.findByCode(code);

        if (couponOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("couponError", "Coupon code not found");
            return "redirect:/cart";
        }

        Coupon coupon = couponOpt.get();

        if (!coupon.isValid()) {
            redirectAttributes.addFlashAttribute("couponError", "Coupon is expired or no longer valid");
            return "redirect:/cart";
        }

        if (coupon.getMinimumOrderAmount() != null && cartTotal < coupon.getMinimumOrderAmount()) {
            redirectAttributes.addFlashAttribute("couponError", 
                    "Minimum order amount is $" + coupon.getMinimumOrderAmount());
            return "redirect:/cart";
        }

        Double discountAmount = coupon.calculateDiscount(cartTotal);

        session.setAttribute("appliedCouponCode", code);
        session.setAttribute("discountAmount", discountAmount);

        String discountText;
        if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
            discountText = coupon.getDiscountValue().intValue() + "% off";
        } else {
            discountText = "$" + coupon.getDiscountValue() + " off";
        }

        redirectAttributes.addFlashAttribute("couponSuccess", 
                "Coupon applied! " + discountText + " (-$" + String.format("%.2f", discountAmount) + ")");

        return "redirect:/cart";
    }

    @PostMapping("/coupon/remove")
    public String removeCoupon(
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        session.removeAttribute("appliedCouponCode");
        session.removeAttribute("discountAmount");

        redirectAttributes.addFlashAttribute("couponInfo", "Coupon removed");
        return "redirect:/cart";
    }
}
