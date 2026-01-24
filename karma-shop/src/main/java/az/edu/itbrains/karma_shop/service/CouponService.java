package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.model.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    
    Optional<Coupon> findByCode(String code);
    
    Coupon createCoupon(Coupon coupon);
    
    List<Coupon> getAllCoupons();
    
    void deleteCoupon(Long id);
    
    boolean isValidCoupon(String code);
}
