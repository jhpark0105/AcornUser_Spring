package com.shop.process.user;

import com.shop.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthProcess {
	
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	
	ResponseEntity<? super LoginRepsonseDto> login(LoginRequestDto dto);
	
//	ResponseEntity<? super LoginRepsonseDto> branchLogin(LoginRequestDto dto);
}
