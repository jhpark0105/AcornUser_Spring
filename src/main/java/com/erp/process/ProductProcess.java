package com.erp.process;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dto.ProductBDto;
import com.erp.dto.ProductDto;
import com.erp.dto.ProductDtoFO;
import com.erp.entity.Product;
import com.erp.entity.Product_B;
import com.erp.repository.ProductBRepository;
import com.erp.repository.ProductRepository;

@Service
public class ProductProcess {
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private ProductBRepository productBRepository;
	
	//대분류 목록 조회
	@Transactional
	public List<ProductBDto> getBProductAll() {
        List<Product_B> productBs = productBRepository.findAll();
        
        return productBs.stream()
                .map(productB -> new ProductBDto(productB.getProductBCode(), productB.getProductBName()))
                .collect(Collectors.toList());
    }
	
	//대분류에서 하나의 데이터 읽기
	@Transactional
    public Product_B getBProductOne(String productBCode) {
    	return productBRepository.findProductBOne(productBCode);
    }
	
	//대분류 등록
	@Transactional
	public String insertBProduct(ProductBDto productBDto) {
    	//DTO -> Entity
		Product_B product_B = new Product_B(productBDto.getProductBCode(), productBDto.getProductBName(), null);
        
		productBRepository.save(product_B);
		
        return "isSuccess";
    }
	
	//대분류 삭제(번호가 없으면 오류)
	@Transactional
	public String deleteBProduct(String productCode) {
		if (!productBRepository.existsById(productCode)) {
            return "해당 번호의 데이터가 존재하지 않습니다.";
        }

		productBRepository.deleteById(productCode);
        
        return "isSuccess";
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	//소분류 목록 조회
	@Transactional
	public List<ProductDto> getProductList() {
	    List<Product> products = productRepository.findAll();
	    
	    return products.stream()
	            .map(product -> new ProductDto(
	                product.getProduct_b().getProductBCode(), product.getProduct_b().getProductBName(), product.getProductCode(), product.getProductName(), product.getProductPrice(), product.getProductEa()))
	            .collect(Collectors.toList());
	}
	//소분류(재고 10개 미만) 목록 조회
	public List<ProductDto> getProductListLessThan10() {
		return productRepository.getProductListLessThan10().stream()
				.map(ProductDto::fromEntity)
				.toList();
	}
	
	//대분류별 소분류 목록 조회
	public List<ProductDtoFO> getProductListWithBCode(String productBCode){
		return productRepository.getProductListWithBCode(productBCode).stream()
				.map(ProductDtoFO::fromEntity)
				.toList();
	}
	
	//소분류 상품 1개의 자료 읽기
	@Transactional
	public Product getProductOne(String productCode) {
	    return productRepository.findProductOne(productCode);
	}
	
	//소분류 등록
	@Transactional
	public String insertProduct(ProductDto productDto) {
		Product_B productB = productBRepository.findProductBOne(productDto.getProductBCode());
		
		//DTO -> Entity
		Product product = new Product(productDto.getProductCode(), productDto.getProductName(), productDto.getProductPrice(), productDto.getProductEa(), productB);
		
		productRepository.save(product);
		
        return "isSuccess";
    }
	
	//소분류 수정
	@Transactional
	public String updateProduct(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getProductCode())) {
            return "해당 번호의 데이터가 존재하지 않습니다.";
        }

        Product_B productB = productBRepository.findProductBOne(productDto.getProductBCode());
    	
		Product product = new Product(productDto.getProductCode(), productDto.getProductName(), productDto.getProductPrice(), productDto.getProductEa(), productB);
        
        productRepository.save(product);
        
        return "success";
    }
	
	//소분류 삭제(번호가 없으면 오류)
	@Transactional
	public String deleteProduct(String productCode) {
		if (!productRepository.existsById(productCode)) {
            return "해당 번호의 데이터가 존재하지 않습니다.";
        }

		productRepository.deleteById(productCode);
        
        return "isSuccess";
	}
}