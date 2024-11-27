package com.erp.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dto.ManagerDto;
import com.erp.entity.Manager;
import com.erp.repository.ManagerRepository;

@Service
public class ManagerProcess {
	@Autowired
	private ManagerRepository managerRepository;
	
	// branchCode로 조회
	public Manager getManager(String branchCode) {
	    return managerRepository.findByBranch_BranchCode(branchCode);
	}
 	
 	// 수정
    public String update(ManagerDto dto) {
        // 매니저 정보 가져오기
    	Manager manager = getManager(dto.getBranchCode());

        if (manager == null) {
            return "해당 지점에 매니저가 존재하지 않습니다."; 
        }
        
        // 기존 매니저 정보에 DTO로 받은 데이터 적용
        manager = dto.toEntity(manager); 
        managerRepository.save(manager);
        
        return "success"; 
    }
}