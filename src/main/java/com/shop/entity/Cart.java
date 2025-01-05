package com.shop.entity;

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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(nullable = true)
    private int quantity;


    public static Cart toEntity(CartDto dto) {
        return Cart.builder()
                .cartId(dto.getCartId())
                .customerId(dto.getCustomerId())
                .productCode(dto.getProductCode())
                .productName(dto.getProductName())
                .productPrice(dto.getProductPrice())
                .quantity(dto.getQuantity())
                .build();
    }

}
