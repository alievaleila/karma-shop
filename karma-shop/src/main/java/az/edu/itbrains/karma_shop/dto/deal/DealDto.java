package az.edu.itbrains.karma_shop.dto.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DealDto {

    private Long id;
    private String name;
    private Double price;
    private Double discountPrice;
    private String imageUrl;
}
