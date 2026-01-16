package az.edu.itbrains.karma_shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Double price;
    private Double discountPrice;
    private String imageUrl;
    private String description;
    private String categoryName;
    private String brandName;
    private String colorName;
}
