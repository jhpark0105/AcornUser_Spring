package com.erp.controller;

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
import com.erp.process.MemberProcess;
import com.erp.dto.MemberDto;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 서버 주소
public class MemberController {
	@Autowired
	private MemberProcess memberProcess;
	
	@GetMapping("/member") // 전체 자료 읽기
	public List<MemberDto> getAllList() {
		return memberProcess.getAllList();
	}
	
	@PostMapping("/member") // 자료 추가
	@Transactional
	public Map<String, Object> insertProcess(@RequestBody MemberDto dto) {
		memberProcess.insert(dto);
		System.out.println(dto.getMemberName());
		return Map.of("Success", true);
	}
	
	@GetMapping("member/{memberId}")
	public Map<String, Object> getMemData(@PathVariable("memberId")String memberId) {
		Member member = memberProcess.getData(memberId);
		return Map.of("memberId",member.getMemberId(),
				"memberName", member.getMemberName(),
				"memberJob", member.getMemberJob(),
				"memberDate", member.getMemberDate(),
				"memberTel", member.getMemberTel(),
				"memberCnt", member.getMemberCnt(),
				"branchCode", member.getBranchCode());
	}
	
	@PutMapping("/member") // 자료 수정
	@Transactional
	public Map<String, Object> updateProcess(@RequestBody MemberDto dto) {
		memberProcess.update(dto);
		return Map.of("Success", true);
	}
	
	@DeleteMapping("/member/{memberId}")
	@Transactional
	public Map<String, Object> deleteProcess(@PathVariable("memberId")String memberId) {
		memberProcess.delete(memberId);
		return Map.of("Success", true);
	}
}
