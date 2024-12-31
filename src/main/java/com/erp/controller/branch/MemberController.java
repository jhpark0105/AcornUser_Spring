package com.erp.controller.branch;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import com.erp.entity.Member;
import com.erp.process.branch.MemberProcess;
import com.erp.dto.MemberDto;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 서버 주소
public class MemberController {
	// 제발 성공해줘 
	@Autowired
	private MemberProcess dao;
	
	@GetMapping("/member") // 전체 자료 읽기
	public List<MemberDto> getAllList() {
		return dao.getAllList();
	}
	
	@PostMapping("/member") // 자료 추가
	public Map<String, Object> insertProcess(@RequestBody MemberDto dto) {
		Map<String, Object> response = dao.insert(dto);
		
		return response;
	}
	
	@GetMapping("member/{memberId}")
	public Map<String, Object> getMemData(@PathVariable("memberId")String memberId) {
		Member member = dao.getData(memberId);
		return Map.of("memberId",member.getMemberId(),
				"memberName", member.getMemberName(),
				"memberJob", member.getMemberJob(),
				"memberDate", member.getMemberDate(),
				"memberTel", member.getMemberTel(),
				"memberCnt", member.getMemberCnt(),
				"branchCode", member.getBranchCode());
	}
	
	@PutMapping("/member/{memberId}") // 자료 수정
	public Map<String, Object> updateProcess(@RequestBody MemberDto dto) {
		dao.update(dto);
		return Map.of("Success", true);
	}
	
	@DeleteMapping("/member/{memberId}")
	public Map<String, Object> deleteProcess(@PathVariable("memberId")String memberId) {
		dao.delete(memberId);
		return Map.of("Success", true);
	}
}