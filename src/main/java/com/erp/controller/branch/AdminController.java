package com.erp.controller.branch;

import java.util.List;

import com.erp.dto.ManagerDto;
import com.erp.entity.Manager;
import com.erp.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.erp.dto.AdminDto;
import com.erp.entity.Admin;
import com.erp.process.branch.AdminProcess;

@RestController
public class AdminController {
	@Autowired
	private AdminProcess adminProcess;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// 쿠키에서 adminId 추출하여 admin 조회
	@GetMapping("/admin/mypage")
	public AdminDto getAdminByToken(@CookieValue(name = "accessToken") String token) {
		// 토큰에서 adminId(id) 추출
		String adminId = jwtProvider.validate(token);

		// 추출한 adminId로 admin 조회
		Admin admin = adminProcess.selectOne(adminId);
		return AdminDto.toDto(admin);
	}
	
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
	public ResponseEntity<String> update(@PathVariable("adminId") String adminId, @RequestBody AdminDto dto) {
		Admin admin = adminProcess.selectOne(adminId);

		if (admin == null) {
			return ResponseEntity.notFound().build();
		}

		// 기존 비밀번호 유지: 비밀번호가 null이거나 비어 있으면 기존 값 사용
		if (dto.getAdminPw() == null || dto.getAdminPw().isEmpty()) {
			dto.setAdminPw(admin.getAdminPw());
		} else if (dto.getAdminPw().startsWith("$2a$")) {
			// 클라이언트가 암호화된 비밀번호를 보낸 경우
			dto.setAdminPw(admin.getAdminPw());
		} else {
			// 평문 비밀번호가 전송된 경우에만 해싱
			String hashedPassword = passwordEncoder.encode(dto.getAdminPw());
			dto.setAdminPw(hashedPassword);
		}
		// 사용자 정보 업데이트
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