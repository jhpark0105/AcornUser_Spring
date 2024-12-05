package com.erp.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.erp.common.ResponseCode;
import com.erp.common.ResponseMessage;

import lombok.Getter;

@Getter
public class BranchSignUpResponseDto extends ResponseDto {
	
	private BranchSignUpResponseDto() {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
	}
	
	public static ResponseEntity<BranchSignUpResponseDto> success() {
		BranchSignUpResponseDto result = new BranchSignUpResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicateId() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicateEmail() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	public static ResponseEntity<ResponseDto> duplicatePhone() {
		ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_PHONE, ResponseMessage.DUPLICATE_PHONE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
}
