package com.erp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	//소분류 코드로 소분류 데이터 조회
	@Query("SELECT p FROM Product p WHERE p.productCode = :productCode")
    Product findProductOne(@Param("productCode") String productCode);
	
	//해당 대분류 코드를 가진 소분류 데이터가 있는지 확인
	@Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.product_b.productBCode = :productBCode")
	boolean existsByProductBCode(@Param("productBCode") String productBCode);

    /**
     * Dashboard 상품 재고 수량 목록 출력을 위한 메서드
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Product p WHERE p.productEa < 10 ORDER BY p.productEa ASC")
	List<Product> getProductListLessThan10();
    
    @Query("SELECT p FROM Product p WHERE p.product_b.productBCode=:productBCode")
    List<Product> getProductListWithBCode(@Param("productBCode")String productBCode);
}