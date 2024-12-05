package com.erp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.NoticeDto;
import com.erp.process.NoticeProcess;

@RestController
@RequestMapping("/dashboard/notice")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardNoticeController {
    private NoticeProcess noticeProcess;
	public DashboardNoticeController(NoticeProcess noticeProcess) {
		this.noticeProcess = noticeProcess;
	}
	
	@GetMapping
	public List<NoticeDto> getCheckedNoticeList(){
		return noticeProcess.getCheckedNoticeList();
	}
}
