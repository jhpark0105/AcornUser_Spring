package com.erp.dto;

import com.erp.entity.Admin;

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
	private String adminId;
	private String adminPw;
	private String adminRole;
	private String adminName;
	private String adminBirth;
	private String adminPhone;
	private String adminPostcode;
	private String adminAddress1;
	private String adminAddress2;
	private String adminTerm1;
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
				.adminPostcode(admin.getAdminPostcode())
				.adminAddress1(admin.getAdminAddress1())
				.adminAddress2(admin.getAdminAddress2())
				.adminTerm1(admin.getAdminTerm1())
				.adminTerm2(admin.getAdminTerm2())
				.adminTerm3(admin.getAdminTerm3())
				.build();
	}
}
