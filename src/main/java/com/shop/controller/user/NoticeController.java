package com.shop.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.NoticeDto;
import com.shop.process.user.NoticeProcess;

@RestController
@RequestMapping("/notice")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticeController {
	@Autowired
	private NoticeProcess noticeProcess;
    //@Autowired
    //private AlarmProcess alarmProcess;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	// 전체 페이징 및 조회
	@GetMapping
	public Page<NoticeDto> readAll(Pageable pageable){
		return noticeProcess.selectAll(pageable);
	}
	
	// 공지 상세
	@GetMapping("/{no}")
	public NoticeDto readOne(@PathVariable("no")int noticeNo) {
		return noticeProcess.selectOne(noticeNo);
	}
	
//	// 검색결과 페이징 및 조회
//	@GetMapping("/search")
//	public Page<NoticeDto> readSearched(
//		@RequestParam(required = false, value = "keyword") String noticeTitle, Pageable pageable) {
//		if(noticeTitle == null || noticeTitle.isEmpty()) { // 검색어를 입력하지 않았을 경우
//			return noticeProcess.selectAll(pageable); // 전체 공지 페이징
//		} else { // 검색어를 입력했을 경우
//			return noticeProcess.selectSearched(noticeTitle, pageable); // 검색결과 페이징
//		}
//	}
	
//	// 공지 작성
//	@PostMapping
//	public Map<String, Object> insertData(@RequestPart("dto") NoticeDto dto, // JSON 데이터
//	        							  @RequestPart(value = "image", required = false) MultipartFile image) { // 이미지 파일) {
////		// 공지사항 등록
////		Map<String, Object> response = noticeProcess.insert(dto, image);
//
////		// 공지사항 번호 가져옴
////		Integer noticeNo = (Integer) response.get("noticeNo");  // Object 타입을 Integer로 캐스팅
////
////		// 알림 메시지 생성
////		String alarmContent = "새로운 공지사항이 등록되었습니다!";
////
////		// 알림 객체 생성 및 초기화
////		alarm alarm = com.erp.entity.alarm.builder()
////				.content(alarmContent)        // 알림 내용
////				.noticeNo((long) noticeNo)    // 공지사항 번호 추가
////				.build();
////
////		// 알림 DB에 저장
////		alarmProcess.saveAlarm(alarm);
////
////		// WebSocket으로 알림 전송
////		messagingTemplate.convertAndSend("/topic/notice", alarmContent);
//
//		return response;
//	}


//	// 공지작성 후 방금 작성한 공지 상세페이지로 전환하기 위해 가장 최근에 작성한 공지번호 가져오기
//	@GetMapping("/latest")
//	public Map<String, Object> getLatestNo(){
//		return noticeProcess.selectLatestNo();
//	}
//
//	//공지사항 삭제
//	@DeleteMapping("/{no}")
//	public Map<String, Object> deleteNotice(@PathVariable("no")int noticeNo) {
//		noticeProcess.deleteNotice(noticeNo);
//
//		Map<String, Object> map = new HashMap<>();
//
//		map.put("isSuccess", true);
//
//		return map;
//	}
}