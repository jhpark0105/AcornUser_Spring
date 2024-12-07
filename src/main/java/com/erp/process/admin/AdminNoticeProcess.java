package com.erp.process.admin;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dto.NoticeDto;
import com.erp.entity.Notice;
import com.erp.repository.NoticeRepository;

@Service
public class AdminNoticeProcess {
	@Autowired
	private NoticeRepository noticeRepository;
	
	// == Create: 새 공지 작성 ========================================================================
	// 공지 작성
	public Map<String, Object> insert(NoticeDto dto) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Notice newData = Notice.of(dto);
			noticeRepository.save(newData);
			response.put("isSuccess", true);
			response.put("message", "공지를 작성하였습니다.");
		} catch (Exception e) {
			response.put("isSuccess", false);
			response.put("message", "공지 작성중 오류발생: " + e.getMessage());
		}
		return response;
	}
	
	// 방금 작성한 공지번호 가져오기
	public Map<String, Object> selectLatestNo() {
		return Map.of("noticeNo", noticeRepository.findFirstByOrderByNoticeNoDesc().getNoticeNo());
	}
}