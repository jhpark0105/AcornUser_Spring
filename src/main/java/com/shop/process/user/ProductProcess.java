package com.shop.process.user;

import com.shop.dto.ProductDto;
import com.shop.dto.ProductDtoFO;
import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductProcess {
    @Autowired
    private ProductRepository productRepository;

    //대분류별 소분류 목록 조회
    public List<ProductDtoFO> getProductListWithBCode(String productBCode){
        return productRepository.getProductListWithBCode(productBCode).stream()
                .map(ProductDtoFO::fromEntity)
                .toList();
    }

    //소분류 목록 조회
    @Transactional
    public List<ProductDto> getProductList() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> ProductDto.builder()
                        .product_b(product.getProduct_b())  // 대분류 객체
                        .productCode(product.getProductCode())  // 상품 소분류 코드
                        .productName(product.getProductName())  // 상품 이름
                        .productPrice(product.getProductPrice())  // 상품 가격
                        .productEa(product.getProductEa())  // 상품 수량
                        .productImagePath(product.getProductImagePath()) // 상품 사진
                        .build())
                .collect(Collectors.toList());
    }

    //소분류 상품 1개의 자료 읽기
    @Transactional
    public Product getProductOne(String productCode) {
        return productRepository.findProductOne(productCode);
    }
}