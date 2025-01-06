package com.shop.dto;

import com.shop.entity.Customer;
import com.shop.entity.Product;
import com.shop.entity.Wishlist;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private int wishlistId;
    private int customerId;
    private String productCode;
    private LocalDateTime createAt;

    // Entity -> DTO 변환
    public static WishlistDto fromEntity(Wishlist wishlist) {
        if (wishlist == null) return null;

        return WishlistDto.builder()
                .wishlistId(wishlist.getWishlistId())
                .customerId(wishlist.getCustomer().getCustomerId())
                .productCode(wishlist.getProduct().getProductCode())
                .createAt(wishlist.getCreateAt())
                .build();
    }
}
