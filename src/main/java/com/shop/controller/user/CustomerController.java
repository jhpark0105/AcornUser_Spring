package com.shop.controller.user;

import com.shop.dto.CustomerDto;
import com.shop.entity.Customer;
import com.shop.process.user.CustomerLoginProcess;
import com.shop.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerLoginProcess customerLoginProcess;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 현재 로그인한 사용자 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<CustomerDto> getCustomerData(
            @CookieValue(name = "accessToken", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 토큰이 없으면 401 응답
        }

        String customerShopid = jwtProvider.validate(token); // 토큰 검증 후 사용자 ID 반환
        if (customerShopid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 유효하지 않은 토큰
        }

        Customer customer = customerLoginProcess.findOne(customerShopid); // 사용자 조회
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 사용자 정보가 없으면 404 응답
        }

        return ResponseEntity.ok(CustomerDto.toDto(customer)); // 사용자 정보 반환
    }

    // 전체 사용자 목록 조회 (관리자용)
    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> findAll() {
        List<CustomerDto> customers = customerLoginProcess.findAll();
        return ResponseEntity.ok(customers);
    }

    // 특정 사용자 정보 조회
    @GetMapping("/{customerShopid}")
    public ResponseEntity<Customer> findOne(@PathVariable("customerShopid") String customerShopid) {
        Customer customer = customerLoginProcess.findOne(customerShopid);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // 특정 사용자 정보 수정
    @PutMapping("/{customerShopid}")
    public ResponseEntity<String> update(
            @PathVariable("customerShopid") String customerShopid,
            @RequestBody CustomerDto dto) {
        Customer customer = customerLoginProcess.findOne(customerShopid);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        // 비밀번호 처리
        if (dto.getCustomerShoppw() != null && !dto.getCustomerShoppw().isEmpty()) {
            customer.setCustomerShoppw(passwordEncoder.encode(dto.getCustomerShoppw()));
        }

        // 사용자 정보 업데이트
        customer.setCustomerName(dto.getCustomerName());
        customer.setCustomerTel(dto.getCustomerTel());
        customer.setCustomerMail(dto.getCustomerMail());
        customerLoginProcess.update(customerShopid, dto);

        return ResponseEntity.ok("success");
    }

    // 특정 사용자 삭제
    @DeleteMapping("/{customerShopid}")
    public ResponseEntity<String> delete(@PathVariable("customerShopid") String customerShopid) {
        Customer customer = customerLoginProcess.findOne(customerShopid);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        customerLoginProcess.delete(customerShopid);
        return ResponseEntity.ok("success");
    }
}