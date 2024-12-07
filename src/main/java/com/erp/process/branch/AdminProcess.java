package com.erp.process.branch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.dto.AdminDto;
import com.erp.entity.Admin;
import com.erp.repository.AdminRepository;

@Service
public class AdminProcess {
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 어드민 계정 전체 조회
	public List<AdminDto> selectAll() {
		List<AdminDto> list = adminRepository.findAll()
				.stream()
				.map(AdminDto :: toDto)
				.collect(Collectors.toList());
		return list;
	}
	
	// 1개의 어드민 계정 조회
	public Admin selectOne(String adminId) {
		Admin admin = adminRepository.findByAdminId(adminId);
		return admin;
	}
	
	// 수정
	public void update(String adminId, AdminDto dto) {
		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null) {
	        throw new IllegalArgumentException("어드민 계정이 없습니다.");
		}
	        admin.setAdminPw(bCryptPasswordEncoder.encode(dto.getAdminPw()));
	        admin.setAdminName(dto.getAdminName());
	        admin.setAdminBirth(dto.getAdminBirth());
	        admin.setAdminPhone(dto.getAdminPhone());
	        admin.setAdminEmail(dto.getAdminEmail());
	        admin.setAdminPostcode(dto.getAdminPostcode());
	        admin.setAdminAddress1(dto.getAdminAddress1());
	        admin.setAdminAddress2(dto.getAdminAddress2());
	        admin.setAdminTerm1(dto.getAdminTerm1());
	        admin.setAdminTerm2(dto.getAdminTerm2());
	        admin.setAdminTerm3(dto.getAdminTerm3());
	        adminRepository.save(admin);
		}

	
	// 삭제
	public void delete(String adminId) {
		adminRepository.deleteById(adminId);
	}
}
