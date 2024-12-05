package com.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.AdminDto;
import com.erp.entity.Admin;
import com.erp.process.AdminProcess;

@RestController
public class AdminController {
	@Autowired
	private AdminProcess adminProcess;
	
	// SELECT (회원 조회)
	@GetMapping("/admin")
	public ResponseEntity<List<AdminDto>> select() {
		List<AdminDto> admins = adminProcess.selectAll();
		return ResponseEntity.ok(admins);
	}
	
	// SELECT (수정/삭제 대상)
	@GetMapping("/admin/{adminId}")
	public ResponseEntity<Admin> selectOne(@PathVariable("adminId")String adminId) {
		Admin admin = adminProcess.selectOne(adminId);
		if(admin != null) {
			return ResponseEntity.ok(admin);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// UPDATE (회원 수정)
	@PutMapping("/admin/{adminId}")
	public ResponseEntity<String> update(@PathVariable("adminId")String adminId, @RequestBody AdminDto dto) {
		Admin admin = adminProcess.selectOne(adminId);
		if(admin == null) {
			return ResponseEntity.notFound().build();
		}
		dto.setAdminId(adminId);
		adminProcess.update(adminId, dto);
		return ResponseEntity.ok("success");
	}
	
	// DELETE (회원 삭제)
	@DeleteMapping("/admin/{adminId}")
	public ResponseEntity<String> delete(@PathVariable("adminId")String adminId) {
		Admin admin = adminProcess.selectOne(adminId);
		if(admin == null) {
			return ResponseEntity.notFound().build();
		}
		adminProcess.delete(adminId);
		return ResponseEntity.ok("success");
	}
}
