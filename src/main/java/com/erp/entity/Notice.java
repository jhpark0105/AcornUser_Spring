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
	private int noticeNo;
	
	@Column(length = 50, nullable = false)
	private String noticeTitle;
	
	@Column(length = 1000, nullable = false)
	private String noticeContent;
	
	@Column(nullable = false, updatable = false)
	private LocalDate noticeReg;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean noticeCheck;
	
	@PrePersist // persist되기 전에 자동호출되어 당일 날짜정보 저장
	protected void onCreate() {
		noticeReg = LocalDate.now();
	}
	
	// Dto -> Entity
	public static Notice of(NoticeDto dto) {
		return Notice.builder()
			.noticeNo(dto.getNoticeNo())
			.noticeTitle(dto.getNoticeTitle())
			.noticeContent(dto.getNoticeTitle())
			.noticeReg(dto.getNoticeReg())
			.noticeCheck(dto.isNoticeCheck()).build();
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
