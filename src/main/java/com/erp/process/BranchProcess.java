package com.erp.process;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dto.BranchDto;
import com.erp.entity.Branch;
import com.erp.repository.BranchRepository;

@Service
public class BranchProcess {

    @Autowired
    private BranchRepository branchRepository;

    // 전체 브랜치 조회
    public List<BranchDto> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches
        		.stream()
        		.map(BranchDto::fromEntity)
        		.collect(Collectors.toList());
    }

    // 특정 브랜치 조회
    public BranchDto getBranchByCode(String branchCode) {
        Branch branch = branchRepository.findById(branchCode).orElse(null);
        return BranchDto.fromEntity(branch);
    }

    // 브랜치 추가
    public String insertBranch(BranchDto dto) {
        Branch branch = dto.toEntity();
        branchRepository.save(branch);
        return "isSuccess";
    }

    // 브랜치 수정
    public String updateBranch(String branchCode, BranchDto dto) {
        Branch branch = branchRepository.findById(branchCode).orElse(null);
        
        // 수정 데이터 적용
        branch.setBranchCode(dto.getBranchCode());
        branch.setBranchPw(dto.getBranchPw());
        branch.setBranchName(dto.getBranchName());
        branch.setBranchTel(dto.getBranchTel());
        branch.setBranchAddress(dto.getBranchAddress());
        branch.setBranchNote(dto.getBranchNote());
        
        branchRepository.save(branch);
        return "isSuccess";
    }

    // 브랜치 삭제
    public String deleteBranch(String branchCode) {
        branchRepository.deleteById(branchCode);
        return "isSuccess";
    }
    
    public BranchDto getBranch(String branchCode) {
		return BranchDto.fromEntity(branchRepository.findById(branchCode).get());
	}
}

