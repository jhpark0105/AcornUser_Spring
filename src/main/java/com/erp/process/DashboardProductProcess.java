package com.erp.process;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.erp.dto.ProductDto;
import com.erp.repository.ProductRepository;

@Service
public class DashboardProductProcess {
    private final ProductRepository productRepository;

    public DashboardProductProcess (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Page<ProductDto> getProductList(int page){
		Sort sort = Sort.by(Sort.Order.asc("productEa"));
		Pageable pageable = PageRequest.of(page, 10, sort);
	    return productRepository.getPartProductList(pageable).map(ProductDto::fromEntity);
	}
}
