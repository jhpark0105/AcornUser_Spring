package com.shop.controller.user;

import com.shop.dto.ProductDto;
import com.shop.dto.ProductDtoFO;
import com.shop.entity.Product;
import com.shop.process.user.ProductProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") //React 서버 주소
public class ProductController {
    @Autowired
    private ProductProcess productProcess;

    // 대분류별 소분류 목록 불러오기
    @GetMapping("/product/order/{productBCode}")
    public List<ProductDtoFO> getProductListWithBCode(@PathVariable("productBCode") String productBCode) {
        return productProcess.getProductListWithBCode(productBCode);
    }

    // 소분류 목록 불러오기
    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return productProcess.getProductList();
    }

    // 소분류에서 하나의 데이터 읽기
    @GetMapping("/product/{productCode}")
    public ProductDto getOne(@PathVariable("productCode") String productCode) {
        Product product = productProcess.getProductOne(productCode);
        return ProductDto.fromEntity(product);
    }
}