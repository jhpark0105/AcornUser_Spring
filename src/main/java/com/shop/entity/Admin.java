package com.shop.entity;

import com.shop.dto.AdminDto;
import com.shop.dto.SignUpRequestDto;

import jakarta.persistence.Column;
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
	@Column(name = "admin_id")
	private String adminId;
	
	@Column(name = "admin_pw")
	private String adminPw;
	
	@Column(name = "admin_role")
	private String adminRole;
	
	@Column(name = "admin_name")
	private String adminName;
	
	@Column(name = "admin_email")
	private String adminEmail;
	
	@Column(name = "admin_birth")
	private String adminBirth;
	
	@Column(name = "admin_phone")
	private String adminPhone;
	
	@Column(name = "admin_postcode")
	private String adminPostcode;
	
	@Column(name = "admin_address1")
	private String adminAddress1;
	
	@Column(name = "admin_address2")
	private String adminAddress2;
	
	@Column(name = "admin_term1")
	private String adminTerm1;
	
	@Column(name = "admin_term2")
	private String adminTerm2;
	
	@Column(name = "admin_term3")
	private String adminTerm3;
	
	
	// toEntity
	// 클라이언트로부터 받아온 데이터를 엔티티에 저장하는 메소드
	public static Admin toEntity(AdminDto dto) {
		return Admin.builder()
				.adminId(dto.getAdminId())
				.adminPw(dto.getAdminPw())
				.adminName(dto.getAdminName())
				.adminBirth(dto.getAdminBirth())
				.adminPhone(dto.getAdminPhone())
				.adminEmail(dto.getAdminEmail())
				.adminPostcode(dto.getAdminPostcode())
				.adminAddress1(dto.getAdminAddress1())
				.adminAddress2(dto.getAdminAddress2())
				.adminTerm1(dto.getAdminTerm1())
				.adminTerm2(dto.getAdminTerm2())
				.adminTerm3(dto.getAdminTerm3())
				.build();
	}
	
	// 회원가입 시 클라이언트로부터 받아온 데이터를 엔티티에 저장하는 메소드
	// 데이터 저장 및 데이터베이스 매핑용 객체를 생성
//	public Admin(SignUpRequestDto dto) {
//		this.adminId = dto.getAdminId();
//		this.adminPw = dto.getAdminPw();
//		this.adminName = dto.getAdminName();
//		this.adminBirth = dto.getAdminBirth();
//		this.adminPhone = dto.getAdminPhone();
//		this.adminEmail = dto.getAdminEmail();
//		this.adminPostcode = dto.getAdminPostcode();
//		this.adminAddress1 = dto.getAdminAddress1();
//		this.adminAddress2 = dto.getAdminAddress2();
//		this.adminTerm1 = dto.getAdminTerm1();
//		this.adminTerm2 = dto.getAdminTerm2();
//		this.adminTerm3 = dto.getAdminTerm3();
//	}
}
