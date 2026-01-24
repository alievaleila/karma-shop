package az.edu.itbrains.karma_shop.config;

import az.edu.itbrains.karma_shop.model.Coupon;
import az.edu.itbrains.karma_shop.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponDataInitializer implements CommandLineRunner {

    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) {
        if (couponRepository.count() == 0) {
            createSampleCoupons();
            log.info("Sample coupons created successfully");
        }
    }

    private void createSampleCoupons() {
        // 10% discount coupon
        Coupon percentageCoupon = Coupon.builder()
                .code("SAVE10")
                .discountType(Coupon.DiscountType.PERCENTAGE)
                .discountValue(10.0)
                .minimumOrderAmount(50.0)
                .maximumDiscountAmount(100.0)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusMonths(6))
                .usageLimit(1000)
                .usedCount(0)
                .isActive(true)
                .build();
        couponRepository.save(percentageCoupon);

        // 20% discount coupon
        Coupon bigSaleCoupon = Coupon.builder()
                .code("BIGSALE20")
                .discountType(Coupon.DiscountType.PERCENTAGE)
                .discountValue(20.0)
                .minimumOrderAmount(100.0)
                .maximumDiscountAmount(200.0)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusMonths(3))
                .usageLimit(500)
                .usedCount(0)
                .isActive(true)
                .build();
        couponRepository.save(bigSaleCoupon);

        // Fixed $15 discount coupon
        Coupon fixedCoupon = Coupon.builder()
                .code("FLAT15")
                .discountType(Coupon.DiscountType.FIXED_AMOUNT)
                .discountValue(15.0)
                .minimumOrderAmount(75.0)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusMonths(6))
                .usageLimit(null) // Unlimited usage
                .usedCount(0)
                .isActive(true)
                .build();
        couponRepository.save(fixedCoupon);

        // Welcome coupon - 5% for new users
        Coupon welcomeCoupon = Coupon.builder()
                .code("WELCOME5")
                .discountType(Coupon.DiscountType.PERCENTAGE)
                .discountValue(5.0)
                .minimumOrderAmount(null) // No minimum
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusYears(1))
                .usageLimit(null)
                .usedCount(0)
                .isActive(true)
                .build();
        couponRepository.save(welcomeCoupon);
    }
}
