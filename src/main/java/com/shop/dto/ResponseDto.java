package com.shop.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shop.common.ResponseCode;
import com.shop.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 응답 관리 DTO
@Getter
@AllArgsConstructor
public class ResponseDto {
	
	private String code;
	private String message;
	
	public static ResponseEntity<ResponseDto> databaseError() {
		ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
}
