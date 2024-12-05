package com.erp.entity;

import java.time.LocalDate;

import com.erp.dto.NoticeDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int noticeNo; // 공지 번호
	
	@Column(length = 50, nullable = false)
	private String noticeTitle; // 공지 제목
	
	@Column(length = 1000, nullable = false)
	private String noticeContent; // 공지 내용
	
	@Column(nullable = false, updatable = false)
	private LocalDate noticeReg; // 공지 작성일
	
	@Column(nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean noticeCheck; // 공지 중요여부
	
	@PrePersist // persist되기 전에 자동호출되어 당일 날짜정보 저장
	protected void onCreate() {
		noticeReg = LocalDate.now();
	}
	
	// Entity -> Dto
	public NoticeDto toDto() {
		return NoticeDto.builder()
			.noticeNo(noticeNo)
			.noticeTitle(noticeTitle)
			.noticeContent(noticeContent)
			.noticeReg(noticeReg)
			.noticeCheck(noticeCheck).build();
	}
}
