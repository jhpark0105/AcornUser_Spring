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
    private int customerId; // 고객 ID
    private String productCode; // 상품 코드
    private String productName; // 상품 이름
    private int productPrice; // 상품 가격
    private String productImagePath; // 상품 이미지 경로
    private int quantity; // 상품 수량

    public static CartDto toDto(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .customerId(cart.getCustomer().getCustomerId())
                .productCode(cart.getProduct().getProductCode())
                .productName(cart.getProduct().getProductName())
                .productPrice(cart.getProduct().getProductPrice())
                .productImagePath(cart.getProduct().getProductImagePath())
                .quantity(cart.getQuantity())
                .build();
    }
}
