package com.erp.dto;

import com.erp.entity.Product;
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
public class ProductDtoFO {	//상품발주 모달을 위한 상품Dto
    private String productCode; //상품 소분류 코드
    private String productName; //상품 이름
    private int productPrice; //상품 가격
    private int productEa; //상품 수량
    private ProductBDto productBDto;
    //Entity -> DTO 변환 메서드
    public static ProductDtoFO fromEntity(Product product) {
        return ProductDtoFO.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productEa(product.getProductEa())
                .productBDto(ProductBDto.fromEntity(product.getProduct_b()))
                .build();
    }
    
    //DTO -> Entity 변환 메서드
    public static Product toEntity(ProductDtoFO dto) {
    	ProductBDto bdto = new ProductBDto();
    	bdto.setProductBCode(dto.productBDto.getProductBCode());
    	bdto.setProductBName(dto.productBDto.getProductBName());    	
        return Product.builder()
                .productCode(dto.productCode)
                .productName(dto.productName)
                .productPrice(dto.productPrice)
                .productEa(dto.productEa)
                .product_b(bdto.toEntity())
                .build();
    }
}