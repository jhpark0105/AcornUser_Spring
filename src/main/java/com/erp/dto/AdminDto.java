package com.erp.dto;

import com.erp.entity.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
	@NotBlank @Pattern(regexp ="^[a-zA-Z가-힣]{2,20}$")
	private String adminId;
	
	@NotBlank @Size(min=8, max=20)
	private String adminPw;
	
	private String adminRole;
	
	@NotBlank @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$")
	private String adminName;
	
	@NotBlank
	private String adminBirth;
	
	@NotBlank @Email @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	private String adminEmail;
	
	@NotBlank @Pattern(regexp ="^[0-9]{11}$")
	private String adminPhone;
	
	@NotBlank
	private String adminPostcode;
	
	@NotBlank
	private String adminAddress1;
	
	@NotBlank
	private String adminAddress2;
	
	//@NotBlank
	private String adminTerm1;
	
	//@NotBlank
	private String adminTerm2;
	private String adminTerm3;
	
	// toDto
	public static AdminDto toDto(Admin admin) {
		return AdminDto.builder()
				.adminId(admin.getAdminId())
				.adminPw(admin.getAdminPw())
				.adminName(admin.getAdminName())
				.adminBirth(admin.getAdminBirth())
				.adminPhone(admin.getAdminPhone())
				.adminEmail(admin.getAdminEmail())
				.adminPostcode(admin.getAdminPostcode())
				.adminAddress1(admin.getAdminAddress1())
				.adminAddress2(admin.getAdminAddress2())
				.adminTerm1(admin.getAdminTerm1())
				.adminTerm2(admin.getAdminTerm2())
				.adminTerm3(admin.getAdminTerm3())
				.build();
	}
}
