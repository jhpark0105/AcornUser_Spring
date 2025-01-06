package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shop.dto.CartDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer; // 고객 참조 (Customer 엔티티)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", referencedColumnName = "product_code", nullable = false)
    @JsonBackReference
    private Product product; // 상품 참조 (Product 엔티티)

    @Column(nullable = true)
    private int quantity;

    public static Cart toEntity(CartDto dto, Customer customer, Product product) {
        return Cart.builder()
                .cartId(dto.getCartId())
                .customer(customer)
                .product(product)
                .quantity(dto.getQuantity())
                .build();
    }
}
