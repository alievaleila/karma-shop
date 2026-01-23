package az.edu.itbrains.karma_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long productId;
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;

    public double getTotal() {
        return this.quantity * this.price;
    }
}
