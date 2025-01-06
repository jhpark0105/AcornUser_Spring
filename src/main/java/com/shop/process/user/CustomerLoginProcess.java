package com.shop.process.user;

import com.shop.dto.CustomerDto;
import com.shop.entity.Customer;
import com.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerLoginProcess {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // 고객 계정 전체 조회
    public List<CustomerDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDto::toDto)
                .collect(Collectors.toList());
    }

    // 1개의 고객 계정 조회
    public Customer findOne(String customerShopid) {
        return customerRepository.findByCustomerShopid(customerShopid);
    }

    // 수정
    public void update(String customerShopid, CustomerDto dto) {
        Customer customer = customerRepository.findByCustomerShopid(customerShopid);
        if (customer == null) {
            throw new IllegalArgumentException("고객 계정을 찾을 수 없습니다.");
        }

        // 비밀번호 처리
        if (dto.getCustomerShoppw() != null && !dto.getCustomerShoppw().isEmpty()) {
            customer.setCustomerShoppw(encoder.encode(dto.getCustomerShoppw()));
        }

        // 사용자 정보 업데이트
        customer.setCustomerName(dto.getCustomerName());
        customer.setCustomerGender(dto.getCustomerGender());
        customer.setCustomerTel(dto.getCustomerTel());
        customer.setCustomerMail(dto.getCustomerMail());
        customer.setCustomerPostcode(dto.getCustomerPostcode());
        customer.setCustomerAddr1(dto.getCustomerAddr1());
        customer.setCustomerAddr2(dto.getCustomerAddr2());

        customerRepository.save(customer);
    }

    // 삭제
    public void delete(String customerShopid) {
        Customer customer = customerRepository.findByCustomerShopid(customerShopid);
        if (customer == null) {
            throw new IllegalArgumentException("고객 계정을 찾을 수 없습니다.");
        }
        customerRepository.delete(customer);
    }
}