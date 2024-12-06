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
import com.erp.dto.NoticeNoOnly;
import com.erp.entity.Notice;
import com.erp.repository.NoticeRepository;

@Service
public class NoticeProcess {
	@Autowired
	private NoticeRepository noticeRepository;
	
	// 상단에 고정할 중요 공지 구분하여 페이징처리
	public Page<NoticeDto> selectAll(Pageable pageable){
	    // 중요공지 전부 가져옴
	    List<Notice> checkedNotice = noticeRepository.findByNoticeCheckOrderByNoticeNoDesc(true);
	    int checkedNoticeCount = checkedNotice.size();

	    // 페이지 크기 설정 (고정 + 일반)
	    int pageSize = 10;
	    
	    // 일반 공지 페이징 계산
	    int normalNoticesPerPage = pageSize - checkedNoticeCount;
	    int normalNoticePageNo = pageable.getPageNumber();
	    
	    // 일반공지를 페이징하여 가져옴
	    Page<Notice> normalNotice = noticeRepository.findByNoticeCheckOrderByNoticeNoDesc(
	        false, PageRequest.of(normalNoticePageNo, normalNoticesPerPage));
	    
	    // 일반 공지 수 계산
	    int normalNoticeCount = (int)normalNotice.getTotalElements();
	    // 총 페이지 수 계산
	    int totalPages = (int)Math.ceil((double)normalNoticeCount / normalNoticesPerPage);
	    // 페이징처리를 위한 totalItems 계산
	    int totalItems = normalNoticeCount + checkedNoticeCount * totalPages;

	    List<NoticeDto> joinedNotice = new ArrayList<>();
	    
	    if(pageable.getPageNumber() >= totalPages) {
			// 페이지번호 요청한계 초과 시: 빈 페이지 반환
			return new PageImpl<NoticeDto>(joinedNotice, pageable, 0);
		} else {
			// 페이지번호 정상: 중요 및 일반공지를 DTO로 변환하여 내용을 리스트에 추가
			joinedNotice.addAll(checkedNotice.stream()
				.map(Notice::toDto).collect(Collectors.toList()));
			joinedNotice.addAll(normalNotice.getContent().stream()
				.map(Notice::toDto).collect(Collectors.toList()));
			
			// 결합된 리스트와 페이징정보를 포함한 PageImpl객체 반환
			return new PageImpl<NoticeDto>(joinedNotice, PageRequest.of(
			    	pageable.getPageNumber(), pageSize), totalItems);
		}
	}

	// 해당 번호 공지 읽기
	public NoticeDto selectOne(int noticeNo) {
	    Notice currentNotice = noticeRepository.findById(noticeNo)
	        .orElseThrow(() -> new NoSuchElementException("해당 번호와 일치하는 공지사항이 없습니다."));

	    // 이전/이후버튼에 해당하는 공지번호 
	    NoticeNoOnly prevNotice = noticeRepository.findFirstByNoticeNoLessThanOrderByNoticeNoDesc(noticeNo);
	    NoticeNoOnly nextNotice = noticeRepository.findFirstByNoticeNoGreaterThanOrderByNoticeNoAsc(noticeNo);

	    return NoticeDto.builder()
	        .noticeNo(currentNotice.getNoticeNo())
	        .noticeTitle(currentNotice.getNoticeTitle())
	        .noticeContent(currentNotice.getNoticeContent())
	        .noticeReg(currentNotice.getNoticeReg())
	        .prevNo(prevNotice != null ? prevNotice.getNoticeNo() : null)
	        .nextNo(nextNotice != null ? nextNotice.getNoticeNo() : null)
	        .build();
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
