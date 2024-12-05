package com.erp.process;

import org.springframework.stereotype.Service;
import com.erp.dto.BranchDto;
import com.erp.repository.BranchRepository;

@Service
public class BranchProcess {
	private BranchRepository repository;
	public BranchProcess(BranchRepository repository) {
		this.repository = repository;
	}
	public BranchDto getBranch(String branchCode) {
		return BranchDto.fromEntity(repository.findById(branchCode).get());
	}
}
