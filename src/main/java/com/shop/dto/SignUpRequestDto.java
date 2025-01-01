package com.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// SignUpRequestDto : 회원가입 시 클라이언트로부터 데이터를 받아오는 DTO
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

	@NotBlank(message = "아이디는 필수 입력 항목입니다.")
	@Pattern(
			regexp = "^[A-Za-z][A-Za-z0-9]{2,20}$",
			message = "아이디는 2자 이상 20자 이하의 영문자와 숫자로 시작해야 합니다."
	)
	private String customerShopid;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Pattern(
			regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
			message = "비밀번호는 8자 이상 20자 이하로 숫자, 특수문자, 영문자를 포함해야 합니다."
	)
	private String customerShoppw;

	@NotBlank(message = "이름은 필수 입력 항목입니다.")
	@Pattern(
			regexp = "^[a-zA-Z가-힣]{2,20}$",
			message = "이름은 2자 이상 20자 이하의 한글 또는 영문자로 입력해야 합니다."
	)
	private String customerName;

	@NotBlank(message = "성별은 필수 입력 항목입니다.")
	private String customerGender;

	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Email(message = "유효한 이메일 주소를 입력해야 합니다.")
	private String customerMail;

	@NotBlank(message = "전화번호는 필수 입력 항목입니다.")
	@Pattern(
			regexp = "^[0-9]{11}$",
			message = "전화번호는 11자리 숫자로 입력해야 합니다."
	)
	private String customerTel;

	@NotBlank(message = "우편번호는 필수 입력 항목입니다.")
	private String customerPostcode;

	@NotBlank(message = "주소는 필수 입력 항목입니다.")
	private String customerAddr1;

	@NotBlank(message = "상세 주소는 필수 입력 항목입니다.")
	private String customerAddr2;
}
