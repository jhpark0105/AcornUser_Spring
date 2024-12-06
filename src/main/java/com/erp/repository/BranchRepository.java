package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
	
	// 브랜치 등록 시 중복 코드 검사 용
	boolean existsByBranchCode(String branchCode);
	Branch findByBranchCode(String branchCode);
}
