package com.shop.entity;

import java.time.LocalDate;

import com.shop.dto.NoticeDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	@Id
	@Column(name = "notice_no")
	private int noticeNo; // 공지 번호
	
	@Column(name = "notice_title", length = 50, nullable = false)
	private String noticeTitle; // 공지 제목
	
	@Column(name = "notice_content", length = 1000, nullable = false)
	private String noticeContent; // 공지 내용
	
	@Column(name = "notice_reg", nullable = false, updatable = false)
	private LocalDate noticeReg; // 공지 작성일
	
	@Column(name = "notice_check", nullable = false, columnDefinition = "tinyint(1) default 0")
	private boolean noticeCheck; // 공지 중요여부
	
	@Column(name = "notice_imagepath", nullable = true, length = 255)
    private String noticeImagePath; //공지에 포함된 이미지 파일의 경로
	
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
			.noticeCheck(noticeCheck)
			.noticeImagePath(noticeImagePath).build();
	}
	
	// Dto -> Entity
	public static Notice of(NoticeDto dto) {
		return Notice.builder()
			.noticeNo(dto.getNoticeNo())
			.noticeTitle(dto.getNoticeTitle())
			.noticeContent(dto.getNoticeContent())
			.noticeReg(dto.getNoticeReg())
			.noticeCheck(dto.isNoticeCheck())
			.noticeImagePath(dto.getNoticeImagePath()).build();
	}
}