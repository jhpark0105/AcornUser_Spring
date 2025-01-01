package com.shop.response;

/**
 * 응답 메시지 상수 모음
 * 
 * 원하는 응답 메시지 추가 가능
 * 
 * 구현 용도 아님.
 */
public interface ResponseMessages {
	String NO_DATA = "조회된 데이터가 없습니다.";
	String READ_SUCCESS = "데이터 조회 성공";
	String INSERT_SUCCESS = "데이터 추가 성공";
	String UPDATE_SUCCESS = "데이터 수정 성공";
	String DELETE_SUCCESS = "데이터 삭제 성공";
}
