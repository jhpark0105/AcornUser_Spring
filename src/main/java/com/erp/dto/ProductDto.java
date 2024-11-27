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
    private String productBCode; //상품 대분류 코드
    private String productBName; //상품 대분류 이름
    private String productCode; //상품 소분류 코드
    private String productName; //상품 이름
    private int productPrice; //상품 가격
    private int productEa; //상품 수량
    
    //Entity -> DTO 변환 메서드
    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productBCode(product.getProduct_b().getProductBCode())
                .productBName(product.getProduct_b().getProductBName())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productEa(product.getProductEa())
                .build();
    }
    
    //DTO -> Entity 변환 메서드
    public Product toEntity(Product_B product_b) {
        return Product.builder()
                .productCode(this.productCode)
                .productName(this.productName)
                .productPrice(this.productPrice)
                .productEa(this.productEa)
                //product_b 테이블에 product_b_code, product_b_name이 있음
                .product_b(product_b)
                .build();
    }
}