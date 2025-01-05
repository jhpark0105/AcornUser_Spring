package com.shop.controller.user;

import com.shop.dto.*;
import com.shop.entity.Customer;
import com.shop.process.user.AuthProcess;
import com.shop.process.user.CustomerLoginProcess;
import com.shop.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthProcess authProcess;
    private final CustomerLoginProcess customerLoginProcess;
    private final JwtProvider jwtProvider;

    // 고객 회원가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        // 회원 가입 처리 및 응답 반환
        ResponseEntity<? super SignUpResponseDto> response = authProcess.signUp(signUpRequestDto);
        return response;
    }

    // 고객 로그인 요청 처리
    @PostMapping("/user/login")
    public ResponseEntity<? super LoginRepsonseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        // 로그인 처리 및 응답 반환
        ResponseEntity<? super LoginRepsonseDto> response = authProcess.login(loginRequestDto);
        return response;
    }

//    // 쿠키에서 customerShopid 추출하여 customer 조회
//    @GetMapping("/user/customername")
//    public CustomerDto getCustomerByToken(@CookieValue(name = "accessToken") String token) {
//        // 토큰에서 adminId(id) 추출
//        System.out.println("Received Token: " + token);
//        String customerShopid = jwtProvider.validate(token);
//        System.out.println("Extracted customerShopid: " + customerShopid);
//
//        // 추출한 customerShopid로 customer 조회
//        Customer customer = customerLoginProcess.findOne(customerShopid);
//        return CustomerDto.toDto(customer);
//    }

    // 로그아웃 요청 처리
    @PostMapping("/logoutProcess")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Authorization 헤더 확인
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Authorization header is missing or invalid");
        }

        String token = authHeader.substring(7); // "Bearer " 이후의 토큰 추출

        // 토큰 검증 (JWT 무효화 로직 필요 시 추가)
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(400).body("Invalid token");
        }


        // 로그아웃 시 쿠키 만료
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true) // 클라이언트 스크립트에서 접근 불가
                .secure(true) // HTTPS에서만 전송
                .path("/") // 쿠키의 유효 경로
                .maxAge(0)  // 만료된 쿠키로 설정
                .sameSite("None") // SameSite 속성 설정
                .build();

        // 로그아웃 성공 메시지와 함께 쿠키 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("로그아웃 성공");
    }
}
