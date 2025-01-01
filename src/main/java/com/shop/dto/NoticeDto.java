package com.shop.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private LocalDate noticeReg;
	private boolean noticeCheck;
	private String noticeImagePath;
	
	// 상세페이지의 이동 버튼 구현용
	private Integer prevNo;
	private Integer nextNo;
}
