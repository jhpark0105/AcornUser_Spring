package com.erp.entity;

import com.erp.dto.AdminDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	@Id
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
	
	
	// toEntity
	public static Admin toEntity(AdminDto dto) {
		return Admin.builder()
				.adminId(dto.getAdminId())
				.adminPw(dto.getAdminPw())
				.adminName(dto.getAdminName())
				.adminBirth(dto.getAdminBirth())
				.adminPhone(dto.getAdminPhone())
				.adminPostcode(dto.getAdminPostcode())
				.adminAddress1(dto.getAdminAddress1())
				.adminAddress2(dto.getAdminAddress2())
				.adminTerm1(dto.getAdminTerm1())
				.adminTerm2(dto.getAdminTerm2())
				.adminTerm3(dto.getAdminTerm3())
				.build();
	}
}
