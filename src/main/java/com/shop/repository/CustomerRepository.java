package com.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // 이름으로 고객 조회
    List<Customer> findByCustomerName(String customerName);

    // 샵 아이디 조회
    Customer findByCustomerShopid(String customerShopid);

    // 회원 가입 시 Id, email, tel 중복 검사용
    boolean existsByCustomerShopid(String customerShopid);
    boolean existsByCustomerMail(String customerMail);
    boolean existsByCustomerTel(String customerTel);

    Optional<Customer> findByCustomerNameAndCustomerTel(String customerName, String customerTel);

    Optional<Customer> findByCustomerShopidAndCustomerNameAndCustomerTel(String customerShopid, String customerName, String customerTel);

}