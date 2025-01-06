package com.shop.repository;

import com.shop.entity.Product;
import com.shop.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByCustomer_CustomerId(int customerId);

    void deleteByCustomer_CustomerIdAndProduct_ProductCode(int customerId, String productCode);

    boolean existsByCustomer_CustomerIdAndProduct_ProductCode(int customerId, String productCode);

//    // 여러 상품 코드를 기반으로 데이터를 조회
//    List<Product> findAllByProductCodeIn(List<String> productCodes);
}
