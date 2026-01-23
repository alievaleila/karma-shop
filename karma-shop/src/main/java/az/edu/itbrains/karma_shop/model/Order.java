package az.edu.itbrains.karma_shop.model;

import az.edu.itbrains.karma_shop.dto.CartItemDto;
import az.edu.itbrains.karma_shop.enums.PaymentMethod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItemDto> cartItems;

    private Double subtotal;
    private Double shipping;
    private Double total;

    private String shippingMethod;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String billingAddress;

    private boolean acceptedTerms;
}
