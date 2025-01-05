package com.shop.process.user;

import com.shop.entity.Customer;
import com.shop.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindIdPwdProcess {

    @Autowired
    private CustomerRepository customerRepository;

    // Shop Id 찾기
    public String findShopIdByNameAndTel(String name, String tel) {
        Optional<Customer> customer = customerRepository.findByCustomerNameAndCustomerTel(name, tel);
        return customer.map(Customer::getCustomerShopid).orElse(null);
    }

    // 임시 비밀번호 생성 및 이메일 발송
    @Transactional
    public boolean sendTemporaryPassword(String shopId, String name, String tel) {
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerShopidAndCustomerNameAndCustomerTel(shopId, name, tel);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            // 임시 비밀번호 생성
            String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);
            customer.setCustomerShoppw(temporaryPassword); // 임시 비밀번호 설정
            customerRepository.save(customer);

            // 이메일 발송 (이메일 발송 로직 구현 필요)
            sendEmail(
                    customer.getCustomerMail(),
                    "임시 비밀번호 발급",
                    "임시 비밀번호: " + temporaryPassword
            );
            return true;
        }
        return false;
    }

    private void sendEmail(String to, String subject, String body) {
        // 이메일 발송 로직 구현 (JavaMailSender 또는 외부 서비스 연동)
        System.out.println("이메일 발송: " + to + ", 제목: " + subject + ", 내용: " + body);
    }
}
