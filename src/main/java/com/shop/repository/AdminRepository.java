package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	
	// 회원가입 시 Id, Email, Phone 중복 검사 용
	boolean existsByAdminId(String adminId);
	boolean existsByAdminEmail(String adminEmail);
	boolean existsByAdminPhone(String adminPhone);
	
	Admin findByAdminId(String adminId);
}