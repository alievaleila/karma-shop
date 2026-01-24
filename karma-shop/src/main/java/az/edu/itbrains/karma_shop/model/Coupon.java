package az.edu.itbrains.karma_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Double discountValue;

    private Double minimumOrderAmount;

    private Double maximumDiscountAmount;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private Integer usageLimit;

    private Integer usedCount;

    @Column(nullable = false)
    private Boolean isActive;

    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return isActive
                && now.isAfter(startDate)
                && now.isBefore(endDate)
                && (usageLimit == null || usedCount < usageLimit);
    }

    public Double calculateDiscount(Double orderTotal) {
        if (!isValid()) {
            return 0.0;
        }

        if (minimumOrderAmount != null && orderTotal < minimumOrderAmount) {
            return 0.0;
        }

        Double discount;
        if (discountType == DiscountType.PERCENTAGE) {
            discount = orderTotal * (discountValue / 100);
        } else {
            discount = discountValue;
        }

        if (maximumDiscountAmount != null && discount > maximumDiscountAmount) {
            discount = maximumDiscountAmount;
        }

        return Math.min(discount, orderTotal);
    }
}
