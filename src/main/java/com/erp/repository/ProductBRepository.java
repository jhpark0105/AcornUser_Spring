package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.entity.Product_B;

public interface ProductBRepository extends JpaRepository<Product_B, String> {
	//대분류에서 1개의 데이터 조회
	@Query(value = "SELECT pb FROM Product_B pb WHERE pb.productBCode=?1")
	Product_B findProductBOne(String productBCode);
}