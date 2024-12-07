package com.erp.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.NoticeDto;
import com.erp.process.branch.NoticeProcess;
import com.erp.process.admin.AdminNoticeProcess;

@RestController
@RequestMapping("/admin-notice")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminNoticeController {
	@Autowired
	private NoticeProcess commonProcess;
	@Autowired
	private AdminNoticeProcess adminProcess;
	
	// == Read: 공지목록 읽기 ========================================================================
	// 전체 페이징 및 조회
	@GetMapping
	public Page<NoticeDto> readAll(Pageable pageable){
		return commonProcess.selectAll(pageable);
	}
	
	// 공지 상세
	@GetMapping("/{no}")
	public NoticeDto readOne(@PathVariable("no")int noticeNo) {
		return commonProcess.selectOne(noticeNo);
	}
	
	// 검색결과 페이징 및 조회
	@GetMapping("/search")
	public Page<NoticeDto> readSearched(
		@RequestParam(required = false, value = "keyword") String noticeTitle, Pageable pageable) {
		if(noticeTitle == null || noticeTitle.isEmpty()) { // 검색어를 입력하지 않았을 경우
			return commonProcess.selectAll(pageable); // 전체 공지 페이징
		} else { // 검색어를 입력했을 경우
			return commonProcess.selectSearched(noticeTitle, pageable); // 검색결과 페이징
		}
	}
	
	// == Create: 새 공지 작성 ========================================================================
	// 공지 작성
	@PostMapping
	public Map<String, Object> insertData(@RequestBody NoticeDto dto) {
		return adminProcess.insert(dto);
	}
	
	// 방금 작성한 공지번호 가져오기
	@GetMapping("/latest")
	public Map<String, Object> getLatestNo(){
		return adminProcess.selectLatestNo();
	}
}
