package com.erp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
	
	@NotBlank @Pattern(regexp ="^[A-Za-z][A-Za-z0-9]{1,19}$")
	private String adminId;
	
	@NotBlank @Size(min=8, max=20) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$")
	private String adminPw;
	
	private String adminRole = "ROLE_ADMIN";
	
	@NotBlank @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$")
	private String adminName;
	
	@NotBlank @Pattern(regexp = "^\\d{8}$")
	private String adminBirth;
	
	@NotBlank @Email
	private String adminEmail;
	
	@NotBlank @Pattern(regexp ="^[0-9]{11}$")
	private String adminPhone;
	
	@NotBlank
	private String adminPostcode;
	
	@NotBlank
	private String adminAddress1;
	
	@NotBlank
	private String adminAddress2;
	
	@NotBlank
	private String adminTerm1;
	
	@NotBlank
	private String adminTerm2;
	
	private String adminTerm3;
}
