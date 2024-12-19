package com.erp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.dto.NoticeNoOnly;
import com.erp.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	// 중요 여부에 따라 글번호 내림차순으로 레코드 요청, 일반공지만 페이징처리
	List<Notice> findByNoticeCheckOrderByNoticeNoDesc(boolean noticeNo);
	Page<Notice> findByNoticeCheckOrderByNoticeNoDesc(boolean noticeNo, Pageable pageable);
	
	// 공지제목에 따른 검색결과 페이징처리
	Page<Notice> findByNoticeTitleContainingOrderByNoticeNoDesc(String keyword, Pageable pageable);

	/* Dashboard 공지사항 목록 출력용 */
	@Query("SELECT n FROM Notice n WHERE n.noticeCheck=true ORDER BY n.noticeReg desc")
	List<Notice> getCheckedNoticeList();

	// 이전/다음버튼 클릭 시 해당하는 공지번호를 가져오기 위한 메서드
	NoticeNoOnly findFirstByNoticeNoLessThanOrderByNoticeNoDesc(int currentNo);
	NoticeNoOnly findFirstByNoticeNoGreaterThanOrderByNoticeNoAsc(int currentNo);
	
	//중요 공지 개수가 5개 이하인지 확인
	@Query("SELECT COUNT(n) FROM Notice n WHERE n.noticeCheck = true")
	long countNoticesWithNoticeCheck();
	
	//공지 등록 시, 가장 큰 공지 번호를 가져오기
	@Query("SELECT MAX(n.noticeNo) FROM Notice n")
	Integer findMaxNoticeNo();
	
	// 공지작성 후 최근 공지번호를 가져오기 위한 메서드
	NoticeNoOnly findFirstByOrderByNoticeNoDesc();
}
