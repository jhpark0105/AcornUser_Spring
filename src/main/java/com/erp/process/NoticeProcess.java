package com.erp.process;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.erp.dto.NoticeDto;
import com.erp.entity.Notice;
import com.erp.repository.NoticeRepository;

@Service
public class NoticeProcess {
	@Autowired
	private NoticeRepository noticeRepository;
	
	// 상단 고정 구분하여 페이징처리
	public Page<NoticeDto> selectAll(Pageable pageable){
		// 전체 공지 수 계산
	    long totalNoticeCount = noticeRepository.count();

	    // 중요공지 전부 가져옴
	    List<Notice> checkedNotice = noticeRepository.findByNoticeCheckOrderByNoticeNoDesc(true);
	    int checkedNoticeCount = checkedNotice.size();

	    // 페이지 크기 설정 (고정 + 일반)
	    int pageSize = 10;
	    
	    // 일반 공지 페이징 계산
	    int normalNoticeSize = pageSize - checkedNoticeCount;
	    int normalNoticePage = pageable.getPageNumber();
	    
	    // 일반공지를 페이징하여 가져옴
	    Page<Notice> normalNotice = noticeRepository.findByNoticeCheckOrderByNoticeNoDesc(
	        false, PageRequest.of(normalNoticePage, normalNoticeSize));

	    List<NoticeDto> joinedNotice = new ArrayList<>();
	    
	    if(normalNotice.isEmpty() && pageable.getPageNumber() > 0) {
			// 요청한 페이지에 일반공지가 없으면 빈 페이지를 반환. 중요공지밖에 없는 경우엔 페이지번호가 0이므로 else 블럭으로 이동
			return new PageImpl<NoticeDto>(joinedNotice, pageable, 0);
		} else {
			// 중요 및 일반공지를 DTO로 변환하여 내용을 리스트에 추가
			joinedNotice.addAll(checkedNotice.stream()
				.map(Notice::toDto).collect(Collectors.toList()));
			joinedNotice.addAll(normalNotice.getContent().stream()
				.map(Notice::toDto).collect(Collectors.toList()));
			
			// 결합된 리스트와 페이징정보를 포함한 PageImpl객체 반환
			return new PageImpl<NoticeDto>(joinedNotice, PageRequest.of(
			    	pageable.getPageNumber(), pageSize), totalNoticeCount);
		}
	}

	// 해당 번호 공지 읽기
	public NoticeDto selectOne(int noticeNo) {
		return noticeRepository.findById(noticeNo)
			.map(Notice::toDto).orElseThrow(()
				-> new NoSuchElementException("해당 번호와 일치하는 공지사항이 없습니다."));
	}
	
	// 검색결과 페이징
	public Page<NoticeDto> selectSearched(String keyword, Pageable pageable){
		int pageSize = 10; // 한 페이지당 공지 수 설정
		return noticeRepository.findByNoticeTitleContainingOrderByNoticeNoDesc(
			keyword, PageRequest.of(pageable.getPageNumber(), pageSize))
				.map(Notice::toDto);
	}

	// 중요 공지 목록 읽기
	public List<NoticeDto> getCheckedNoticeList() {
		return noticeRepository.getCheckedNoticeList()
					.stream().map(Notice::toDto).collect(Collectors.toList());
	}
}
