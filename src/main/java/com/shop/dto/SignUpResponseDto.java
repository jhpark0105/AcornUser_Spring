package com.shop.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shop.common.ResponseCode;
import com.shop.common.ResponseMessage;

import lombok.Getter;

// SignUpResponseDto : 회원가입 과정에서 발생하는 다양한 응답을 관리하는 DTO
@Getter
public class SignUpResponseDto extends ResponseDto {
	
	private SignUpResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	// 회원가입 성공 시 응답 메소드
	public static ResponseEntity<SignUpResponseDto> success() {
		SignUpResponseDto result = new SignUpResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	// 아이디 중복 시 응답 메소드
	public static ResponseEntity<ResponseDto> duplicateId() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	// 이메일 중복 시 응답 메소드
	public static ResponseEntity<ResponseDto> duplicateEmail() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	// 전화번호 중복 시 응답 메소드
	public static ResponseEntity<ResponseDto> duplicatePhone() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_PHONE, ResponseMessage.DUPLICATE_PHONE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
