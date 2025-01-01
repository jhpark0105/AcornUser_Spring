package com.shop.controller.user;

import com.shop.dto.LoginRepsonseDto;
import com.shop.dto.LoginRequestDto;
import com.shop.dto.SignUpRequestDto;
import com.shop.dto.SignUpResponseDto;
import com.shop.process.user.AuthProcess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthProcess authProcess;

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

    // 로그아웃 요청 처리
    @PostMapping("/logoutProcess")
    public ResponseEntity<?> logout() {
        // 로그아웃 시 쿠키 만료
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true) // 클라이언트 스크립트에서 접근 불가
                .secure(true) // HTTPS에서만 전송
                .path("/") // 쿠키의 유효 경로
                .maxAge(0)  // 만료된 쿠키로 설정
                .sameSite("None") // SameSite 속성 설정
                .build();

        // 로그아웃 성골 메시지와 함께 쿠키 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("로그아웃 성공");
    }
}
