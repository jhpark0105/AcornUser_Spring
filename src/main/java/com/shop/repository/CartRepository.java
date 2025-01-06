package com.shop.repository;

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer_CustomerId(int customerId);

    // 특정 고객 ID로 장바구니 비우기 삭제
    void deleteByCustomer_CustomerId(int customerId);

    List<Cart> findByCustomer_CustomerShopid(String customerShopid);

}
