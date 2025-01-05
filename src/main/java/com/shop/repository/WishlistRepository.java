package com.shop.repository;

import com.shop.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByCustomer_CustomerId(int customerId); // customerId에 해당하는 위시리스트 조회

    // 특정 고객 ID와 상품 코드로 위시리스트 삭제
    void deleteByCustomer_CustomerIdAndProduct_ProductCode(int customerId, String productCode);

    // 특정 고객 ID와 상품 코드로 위시리스트 존재 여부 확인
    boolean existsByCustomer_CustomerIdAndProduct_ProductCode(int customerId, String productCode);
}
