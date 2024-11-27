package com.erp.dto;

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
public class ProductBDto {
	private String productBCode; //상품 대분류 코드
	private String productBName; //상품 대분류 이름
	
	//Entity -> DTO 변환 메서드
    public static ProductBDto fromEntity(Product_B productB) {
        return ProductBDto.builder()
                .productBCode(productB.getProductBCode())
                .productBName(productB.getProductBName())
                .build();
    }
    
    //DTO -> Entity 변환 메서드
    public Product_B toEntity() {
        return Product_B.builder()
                .productBCode(this.productBCode)
                .productBName(this.productBName)
                .build();
    }
}