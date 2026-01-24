package az.edu.itbrains.karma_shop.model;

import az.edu.itbrains.karma_shop.enums.OrderStatus;
import az.edu.itbrains.karma_shop.enums.PaymentMethod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // Billing Information
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;

    // Address Information
    private String country;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String zipCode;

    // Order Notes
    private String orderNotes;

    // Shipping Information
    private boolean shipToDifferentAddress;
    private String shippingFirstName;
    private String shippingLastName;
    private String shippingCountry;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingDistrict;
    private String shippingZipCode;

    // Pricing
    private Double subtotal;
    private Double shippingCost;
    private Double discount;
    private String couponCode;
    private Double total;

    // Payment & Status
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    private boolean acceptedTerms;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (orderNumber == null) {
            orderNumber = generateOrderNumber();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}
