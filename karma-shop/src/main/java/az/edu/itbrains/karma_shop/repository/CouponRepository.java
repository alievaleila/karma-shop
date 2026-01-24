package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    Optional<Coupon> findByCodeIgnoreCase(String code);
    
    boolean existsByCodeIgnoreCase(String code);
}
