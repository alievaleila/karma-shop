package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.Coupon;
import az.edu.itbrains.karma_shop.repository.CouponRepository;
import az.edu.itbrains.karma_shop.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Optional<Coupon> findByCode(String code) {
        return couponRepository.findByCodeIgnoreCase(code);
    }

    @Override
    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        if (coupon.getUsedCount() == null) {
            coupon.setUsedCount(0);
        }
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public boolean isValidCoupon(String code) {
        return couponRepository.findByCodeIgnoreCase(code)
                .map(Coupon::isValid)
                .orElse(false);
    }
}
