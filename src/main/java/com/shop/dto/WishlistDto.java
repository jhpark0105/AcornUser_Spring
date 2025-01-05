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
    private Customer customer;
    private Product product;

    //DTO -> Entity
    public Wishlist toEntity() {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistId(wishlistId);
        wishlist.setCustomer(customer);
        wishlist.setProduct(product);
        wishlist.setCreateAt(createAt);
        return wishlist;
    }

    //Entity -> DTO
    public static WishlistDto fromEntity(Wishlist wishlist) {
        return WishlistDto.builder()
                .wishlistId(wishlist.getWishlistId())
                .customerId(wishlist.getCustomer().getCustomerId())
                .productCode(wishlist.getProduct().getProductCode())
                .build();
    }
}
