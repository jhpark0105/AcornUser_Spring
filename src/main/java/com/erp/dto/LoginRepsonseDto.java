package com.erp.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.erp.common.ResponseCode;
import com.erp.common.ResponseMessage;

import lombok.Getter;

@Getter
public class LoginRepsonseDto extends ResponseDto {
	
	private String token;
	private int expirationTime;
	
	private LoginRepsonseDto(String token) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.token = token;
		this.expirationTime = 3600;
	}
	
	public static ResponseEntity<LoginRepsonseDto> success(String token) {
		LoginRepsonseDto result = new LoginRepsonseDto(token);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> loginFailed() {
		ResponseDto result = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}
