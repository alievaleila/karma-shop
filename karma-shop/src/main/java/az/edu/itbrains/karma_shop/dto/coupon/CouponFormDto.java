package az.edu.itbrains.karma_shop.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponFormDto {
    private Long id;
    private String code;
    private Integer discountPercent;
    private Double minOrderAmount;
    private LocalDate expiryDate;
    private Boolean active;
}
