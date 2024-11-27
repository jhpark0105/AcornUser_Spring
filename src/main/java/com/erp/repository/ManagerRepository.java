package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, String>{
	// branchCode로 매니저 조회
    Manager findByBranch_BranchCode(String branchCode);
}