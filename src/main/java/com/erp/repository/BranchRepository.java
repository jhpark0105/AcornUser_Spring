package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
	boolean existsByBranchCode(String branchCode);
	Branch findByBranchCode(String branchCode);
}
