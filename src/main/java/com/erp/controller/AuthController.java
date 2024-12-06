package com.erp.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.BranchSignUpRequestDto;
import com.erp.dto.LoginRepsonseDto;
import com.erp.dto.LoginRequestDto;
import com.erp.dto.SignUpRequestDto;
import com.erp.dto.SignUpResponseDto;
import com.erp.process.AuthProcess;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthProcess authProcess; // AuthProcess 의존성 주입
	
	// 어드민 회원가입 요청 처리
	@PostMapping("/admin/signup")
	public ResponseEntity<? super SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto requestBody) {
		// 어드민 역할이 없거나 비어있다면 기본값 설정
		if (requestBody.getAdminRole() == null || requestBody.getAdminRole().isBlank()) {
			requestBody.setAdminRole("ROLE_ADMIN"); // 기본값 설정
	    }
		
		// 회원가입 처리 및 응답 반환
		ResponseEntity<? super SignUpResponseDto> response = authProcess.signUp(requestBody);
		return response;
	}
	
	// 어드민 로그인 요청 처리
	@PostMapping("/admin/login")
	public ResponseEntity<? super LoginRepsonseDto> login(@RequestBody @Valid LoginRequestDto requestBody) {
		// 로그인 처리 및 응답 반환
		ResponseEntity<? super LoginRepsonseDto> response = authProcess.login(requestBody);
		return response;
	}
	
	// 브랜치 등록 요청 처리
	@PostMapping("/main/signup")
	public ResponseEntity<? super SignUpResponseDto> branchSignup(@RequestBody @Valid BranchSignUpRequestDto requestBody) {
		// 어드민 역할이 없거나 비어있다면 기본값 설정
		if (requestBody.getBranchRole() == null || requestBody.getBranchRole().isBlank()) {
			requestBody.setBranchRole("ROLE_BRANCH"); // 기본값 설정
	    }
		
		// 브랜치 등록 처리 및 응답 반환
		ResponseEntity<? super SignUpResponseDto> response = authProcess.branchSignUp(requestBody);
		return response;
	}
	
	// 브랜치 로그인 요청 처리
	@PostMapping("/main/login")
	public ResponseEntity<? super LoginRepsonseDto> branchLogin(@RequestBody @Valid LoginRequestDto requestBody) {
		// 로그인 처리 및 응답 반환
		ResponseEntity<? super LoginRepsonseDto> response = authProcess.branchLogin(requestBody);
		return response;
	}
	
	// 로그아웃 요청 처리
	@PostMapping("/logoutProcess")
	public ResponseEntity<?> logout() {
		// 로그아웃 시 쿠키를 만료시키기 위한 설정
	    ResponseCookie cookie = ResponseCookie.from("accessToken", "")
	            .httpOnly(true) // 클라이언트 스크립트에서 접근 불가
	            .secure(true) // HTTPS에서만 전송
	            .path("/") // 쿠키의 유효 경로
	            .maxAge(0)  // 만료된 쿠키로 설정
	            .sameSite("None") // SameSite 속성 설정
	            .build();
	    
	    System.out.println("Set-Cookie: " + cookie.toString()); // 쿠키 정보 로그
	    
	    // 로그아웃 성공 메시지와 함께 쿠키 설정
	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
	            .body("로그아웃 성공");
	}
}
