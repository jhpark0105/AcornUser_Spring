package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // 이름으로 고객 조회
    List<Customer> findByCustomerName(String customerName);
}
