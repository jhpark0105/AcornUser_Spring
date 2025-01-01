package com.shop.common;

// HTTP Status 응답 코드 인터페이스
public interface ResponseCode {
	
	// HTTP Status 200
	String SUCCESS = "SU";
	
	// HTTP Status 400
	String VALIDATION_FAILED = "VF";
	String DUPLICATE_ID = "DI";
	String DUPLICATE_EMAIL = "DM";
	String DUPLICATE_PHONE = "DP";
	String NOT_EXISTED_USER = "NU";
	String NOT_EXISTED_BOARD = "NB";
	
	// HTTP Status 401
	String SIGN_IN_FAIL = "SF";
	String AUTHORIZATION_FAIL = "AF";
	
	// HTTP status 403
	String NO_PERMISSION = "NP";
	
	// HTTP status 500
	String DATABASE_ERROR = "DBE";
}
