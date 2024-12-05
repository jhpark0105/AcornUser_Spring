package com.erp.dto;

import com.erp.entity.Product;
import com.erp.entity.Product_B;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productCode; //상품 코드
    private String productName; //상품 이름
    private int productPrice; //상품 가격
    private int productEa; //상품 수량
    private Product_B product_b; //대분류
    private String productBCode; //대분류 코드
    private String productBName; //대분류 이름
    
    // Entity -> DTO 변환 메서드
    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
            .product_b(product.getProduct_b()) //대분류 전체를 포함
            .productCode(product.getProductCode())
            .productName(product.getProductName())
            .productPrice(product.getProductPrice())
            .productEa(product.getProductEa())
            .productBCode(product.getProduct_b() != null ? product.getProduct_b().getProductBCode() : null)
            .productBName(product.getProduct_b() != null ? product.getProduct_b().getProductBName() : null)
            .build();
    }
}