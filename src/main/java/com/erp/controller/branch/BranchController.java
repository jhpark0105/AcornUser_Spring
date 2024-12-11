package com.erp.controller.branch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.BranchDto;
import com.erp.process.branch.BranchProcess;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin/mypage")
public class BranchController {
	
	@Autowired
	private BranchProcess branchProcess;
	
	// 브랜치 목록 조회
    @GetMapping("/list")
    public List<BranchDto> getAllBranches() {
        return branchProcess.getAllBranches();
    }

    // 브랜치 상세 조회
    @GetMapping("/{branchCode}")
    public BranchDto getBranch(@PathVariable("branchCode") String branchCode) {
        return branchProcess.getBranchByCode(branchCode);
    }

    // 브랜치 추가
    @PostMapping("/insert")
    public String insertBranch(@RequestBody BranchDto dto) {
        return branchProcess.insertBranch(dto);
    }

    // 브랜치 수정
    @PutMapping("/update/{branchCode}")
    public String updateBranch(@PathVariable("branchCode") String branchCode, @RequestBody BranchDto dto) {
        return branchProcess.updateBranch(branchCode, dto);
    }

    // 브랜치 삭제
    @DeleteMapping("/delete/{branchCode}")
    public String deleteBranch(@PathVariable("branchCode") String branchCode) {
        return branchProcess.deleteBranch(branchCode);
    }
}
