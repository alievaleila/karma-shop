package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.dto.coupon.CouponFormDto;
import az.edu.itbrains.karma_shop.model.Coupon;
import az.edu.itbrains.karma_shop.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponRepository couponRepository;

    @GetMapping
    public String listCoupons(Model model) {
        model.addAttribute("currentPage", "coupons");
        model.addAttribute("pageTitle", "Kuponlar");

        List<CouponFormDto> coupons = couponRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        model.addAttribute("coupons", coupons);

        return "admin/coupons";
    }

    @GetMapping("/add")
    public String addCouponForm(Model model) {
        model.addAttribute("currentPage", "coupons");
        model.addAttribute("pageTitle", "Yeni Kupon");
        model.addAttribute("coupon", new CouponFormDto());
        return "admin/coupon-form";
    }

    @PostMapping("/add")
    public String addCoupon(@ModelAttribute CouponFormDto couponForm, RedirectAttributes redirectAttributes) {
        Coupon coupon = new Coupon();
        mapFormToCoupon(couponForm, coupon);
        couponRepository.save(coupon);
        redirectAttributes.addFlashAttribute("successMessage", "Kupon uğurla əlavə edildi!");
        return "redirect:/admin/coupons";
    }

    @GetMapping("/edit/{id}")
    public String editCouponForm(@PathVariable Long id, Model model) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kupon tapılmadı"));

        model.addAttribute("currentPage", "coupons");
        model.addAttribute("pageTitle", "Kuponu Redaktə Et");
        model.addAttribute("coupon", toDto(coupon));

        return "admin/coupon-form";
    }

    @PostMapping("/edit/{id}")
    public String editCoupon(@PathVariable Long id, @ModelAttribute CouponFormDto couponForm, RedirectAttributes redirectAttributes) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kupon tapılmadı"));

        mapFormToCoupon(couponForm, coupon);
        couponRepository.save(coupon);
        redirectAttributes.addFlashAttribute("successMessage", "Kupon uğurla yeniləndi!");
        return "redirect:/admin/coupons";
    }

    @PostMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        couponRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Kupon uğurla silindi!");
        return "redirect:/admin/coupons";
    }

    private CouponFormDto toDto(Coupon coupon) {
        CouponFormDto dto = new CouponFormDto();
        dto.setId(coupon.getId());
        dto.setCode(coupon.getCode());
        dto.setDiscountPercent(coupon.getDiscountValue() != null ? coupon.getDiscountValue().intValue() : null);
        dto.setMinOrderAmount(coupon.getMinimumOrderAmount());
        dto.setExpiryDate(coupon.getEndDate() != null ? coupon.getEndDate().toLocalDate() : null);
        dto.setActive(coupon.getIsActive() != null && coupon.getIsActive());
        return dto;
    }

    private void mapFormToCoupon(CouponFormDto form, Coupon coupon) {
        coupon.setCode(form.getCode().toUpperCase());
        coupon.setDiscountType(Coupon.DiscountType.PERCENTAGE);
        coupon.setDiscountValue(form.getDiscountPercent() != null ? form.getDiscountPercent().doubleValue() : 0.0);
        coupon.setMinimumOrderAmount(form.getMinOrderAmount());
        coupon.setIsActive(form.getActive() != null && form.getActive());
        
        if (form.getExpiryDate() != null) {
            coupon.setEndDate(form.getExpiryDate().atTime(23, 59, 59));
        }
        if (coupon.getStartDate() == null) {
            coupon.setStartDate(java.time.LocalDateTime.now());
        }
    }
}
