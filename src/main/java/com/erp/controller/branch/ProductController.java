package com.erp.controller.branch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.ProductBDto;
import com.erp.dto.ProductDto;
import com.erp.dto.ProductDtoFO;
import com.erp.entity.Product;
import com.erp.entity.Product_B;
import com.erp.process.branch.ProductProcess;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 서버 주소
public class ProductController {
	@Autowired
    private ProductProcess productProcess;
	
	//대분류 목록 불러오기
    @GetMapping("/productB")
    public List<ProductBDto> listBProcess() {
        return productProcess.getBProductAll();
    }
    
    //대분류에서 하나의 자료 읽기
  	@GetMapping("/productB/{productBCode}")
  	public Product_B getBOne(@PathVariable("productBCode") String productBCode) {
  		return productProcess.getBProductOne(productBCode);
  	}

    //대분류 추가
  	@PostMapping("/productB")
  	public Map<String, Object> insertBProcess(@RequestBody ProductBDto productBDto) {
  	    Map<String, Object> response = productProcess.insertBProduct(productBDto);
  	    
  	    return response;
  	}
    
    //대분류 삭제
    @DeleteMapping("/productB/{productBCode}")
    public Map<String, Object> deleteBProcess(@PathVariable("productBCode") String productBCode) {
    	Map<String, Object> response = productProcess.deleteBProduct(productBCode);
        
        return response;
    }
    
    //소분류 목록 불러오기
    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return productProcess.getProductList();
    }
    
    //소분류 목록 불러오기
    @GetMapping("/product/dashboard")
    public List<ProductDto> getProductListLessThan10() {
        return productProcess.getProductListLessThan10();
    }
    
	//대분류별 소분류 목록 불러오기
    @GetMapping("/product/order/{productBCode}")
    public List<ProductDtoFO> getProductListWithBCode(@PathVariable("productBCode")String productBCode) {
        return productProcess.getProductListWithBCode(productBCode);
    }
    
    //소분류에서 하나의 데이터 읽기
    @GetMapping("/product/{productCode}")
    public ProductDto getOne(@PathVariable("productCode") String productCode) {
        Product product = productProcess.getProductOne(productCode);
        
        return ProductDto.fromEntity(product);
    }
    
    //대분류 목록 불러오기
    @GetMapping("/product/product-b")
    public ResponseEntity<List<Product_B>> getProductBList() {
        List<Product_B> productBs = productProcess.getProductBList();
        return ResponseEntity.ok(productBs);
    }

    //소분류 추가
    @PostMapping("/product")
    public Map<String, Object> insertProcess(@RequestBody ProductDto productDto) {
    	Map<String, Object> response = productProcess.insertProduct(productDto);
    	
        return response;
    }
    
    //소분류 수정
  	@PutMapping("/product/edit/{productCode}")
  	public Map<String, Object> updateProcess(@RequestBody ProductDto productDto,
  											 @PathVariable("productCode") String productCode) {
  		productProcess.updateProduct(productDto);
  		
  		Map<String, Object> map = new HashMap<>();
  		
  		map.put("isSuccess", true);
  		
  		return map;
  	}
    
    //소분류 삭제
    @DeleteMapping("/product/{productCode}")
    public Map<String, Object> deleteProcess(@PathVariable("productCode") String productCode) {
    	productProcess.deleteProduct(productCode);
        
    	Map<String, Object> map = new HashMap<>();
  		
  		map.put("isSuccess", true);
  		
  		return map;
    }
}