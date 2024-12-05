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
	
	private final AuthProcess authProcess;
	
	@PostMapping("/admin/signup")
	public ResponseEntity<? super SignUpResponseDto> signup(@RequestBody @Valid SignUpRequestDto requestBody) {
		
		if (requestBody.getAdminRole() == null || requestBody.getAdminRole().isBlank()) {
			requestBody.setAdminRole("ROLE_ADMIN"); // 기본값 설정
	    }
		
		ResponseEntity<? super SignUpResponseDto> response = authProcess.signUp(requestBody);
		return response;
	}
	
	@PostMapping("/admin/login")
	public ResponseEntity<? super LoginRepsonseDto> login(@RequestBody @Valid LoginRequestDto requestBody) {
		ResponseEntity<? super LoginRepsonseDto> response = authProcess.login(requestBody);
		return response;
	}
	
	@PostMapping("/main/signup")
	public ResponseEntity<? super SignUpResponseDto> branchSignup(@RequestBody @Valid BranchSignUpRequestDto requestBody) {
		
		if (requestBody.getBranchRole() == null || requestBody.getBranchRole().isBlank()) {
			requestBody.setBranchRole("ROLE_BRANCH"); // 기본값 설정
	    }
		
		ResponseEntity<? super SignUpResponseDto> response = authProcess.branchSignUp(requestBody);
		return response;
	}
	
	// branch 로그인
	@PostMapping("/main/login")
	public ResponseEntity<? super LoginRepsonseDto> branchLogin(@RequestBody @Valid LoginRequestDto requestBody) {
		ResponseEntity<? super LoginRepsonseDto> response = authProcess.branchLogin(requestBody);
		return response;
	}
	
	@PostMapping("/logoutProcess")
	public ResponseEntity<?> logout() {
	    ResponseCookie cookie = ResponseCookie.from("accessToken", "")
	            .httpOnly(true)
	            .secure(true)
	            .path("/")
	            .maxAge(0)  // 만료된 쿠키로 설정
	            .sameSite("None")
	            .build();
	    System.out.println("Set-Cookie: " + cookie.toString()); // 로그로 확인
	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
	            .body("로그아웃 성공");
	}
}
