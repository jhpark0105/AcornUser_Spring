package com.shop.dto;

import com.shop.entity.Cart;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long cartId;
    private int customerId;
    private String productCode;
    private String productName;
    private int productPrice;
    private int quantity;

    public static CartDto toDto(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCustomerId())
                .productCode(cart.getProductCode())
                .productName(cart.getProductName())
                .productPrice(cart.getProductPrice())
                .quantity(cart.getQuantity())
                .build();
    }
}
