package com.shop.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 응답 JSON 데이터에 여러 메타 데이터를 첨부하여 전송하기 위한 클래스.
 * 
 * com.erp.response.ResponseMessages 인터페이스의 상수 사용.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseJson {
	
	@Builder.Default
	private HttpStatus status = HttpStatus.NO_CONTENT;
	
	// 클라이언트에게 보여줄 메시지. com.erp.response.ResponseMessages 인터페이스의 상수 사용.
	private String message;
	
	// 클라이언트에게 전송하고자 하는 데이터. 
	private Object data;
}
