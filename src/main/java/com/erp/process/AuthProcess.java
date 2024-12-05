package com.erp.process;

import org.springframework.http.ResponseEntity;

import com.erp.dto.BranchSignUpRequestDto;
import com.erp.dto.LoginRepsonseDto;
import com.erp.dto.LoginRequestDto;
import com.erp.dto.SignUpRequestDto;
import com.erp.dto.SignUpResponseDto;

public interface AuthProcess {
	
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	
	ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto);
	
	ResponseEntity<? super SignUpResponseDto> branchSignUp(BranchSignUpRequestDto dto);
	
	ResponseEntity<? super LoginRepsonseDto> branchLogin(LoginRequestDto dto);
}
