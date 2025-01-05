package com.shop.repository;

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByCustomerId(int customerId);

    void deleteByCustomerId(int customerId);

    void deleteByCustomerIdAndProductCode(int customerId, String productCode);
}
