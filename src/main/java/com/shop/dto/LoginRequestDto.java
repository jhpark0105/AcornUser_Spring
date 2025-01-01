package com.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//LoginRequestDto : 로그인 시 클라이언트로부터 데이터를 받아오는 DTO
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
	@NotBlank
	private String id;
	@NotBlank
	private String password;
}
