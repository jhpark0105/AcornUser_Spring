package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// SELECT (회원 조회)
	@GetMapping("/admin")
	public ResponseEntity<List<AdminDto>> select() {
		List<AdminDto> admins = adminProcess.selectAll();
		return ResponseEntity.ok(admins);
	}
	
	// INSERT (회원 가입)
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody AdminDto dto) {
		try {
	        adminProcess.insert(dto);
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("message", "회원가입 완료");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", false);
	        response.put("message", "회원가입 오류 : " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
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
	
	// 로그인 인증
	@PostMapping("/loginProcess")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
	    try {
	        String id = credentials.get("id");
	        String password = credentials.get("password");

	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(id, password)
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        Map<String, String> response = new HashMap<>();
	        response.put("adminId", authentication.getName());
	        response.put("authorities", authentication.getAuthorities().toString());

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        System.out.println("로그인 인증 오류 : " + e);
	        return ResponseEntity.status(401).body("로그인 실패");
	    }
	}
	
	// 로그인 인증 후 계정 정보 담기
	@GetMapping("/loginSuccess")
	public ResponseEntity<Map<String, String>> loginSuccess() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String adminId = authentication.getName();
		String authorities = authentication.getAuthorities().toString();
		
		Map<String, String> adminInfo = new HashMap<>();
		adminInfo.put("adminId", adminId);
		adminInfo.put("authorities", authorities);
		
		return ResponseEntity.ok(adminInfo);
	}
	
	// 로그아웃
	@GetMapping("/logoutSuccess")
	public ResponseEntity<?> logoutSucess() {
		return ResponseEntity.ok().build();
	}
}
