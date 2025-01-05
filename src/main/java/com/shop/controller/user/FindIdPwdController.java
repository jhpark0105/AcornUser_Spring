package com.shop.controller.user;

import com.shop.dto.CustomerDto;
import com.shop.process.user.FindIdPwdProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindIdPwdController {

    @Autowired
    private FindIdPwdProcess findIdPwdProcess;

    // Shop ID 찾기
    @PostMapping("/find-shopid")
    public ResponseEntity<?> findShopId(@RequestBody CustomerDto customerDto) {
        String shopId = findIdPwdProcess.findShopIdByNameAndTel(customerDto.getCustomerName(), customerDto.getCustomerTel());
        if (shopId != null) {
            return ResponseEntity.ok().body(shopId);
        } else {
            return ResponseEntity.status(404).body("해당 회원 정보를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 찾기
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody CustomerDto customerDto) {
        boolean isSent = findIdPwdProcess.sendTemporaryPassword(
                customerDto.getCustomerShopid(),
                customerDto.getCustomerName(),
                customerDto.getCustomerTel()
        );
        if (isSent) {
            return ResponseEntity.ok().body("임시 비밀번호가 이메일로 발송되었습니다.");
        } else {
            return ResponseEntity.status(404).body("해당 회원 정보를 찾을 수 없습니다.");
        }
    }

}
