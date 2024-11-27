package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	// 동일 아이디 중복 방지, 동일 어드민ID가 존재하면 true, 안하면 false 리턴
	boolean existsByAdminId(String adminId);
	Admin findByAdminId(String adminId);
}