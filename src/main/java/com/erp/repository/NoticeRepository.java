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

	// 이전/다음버튼 클릭 시 해당하는 공지번호 쿼리용
	NoticeNoOnly findFirstByNoticeNoLessThanOrderByNoticeNoDesc(int currentNo);
	NoticeNoOnly findFirstByNoticeNoGreaterThanOrderByNoticeNoAsc(int currentNo);
	
	// 공지작성 후 최근 공지번호 가져오기
	NoticeNoOnly findFirstByOrderByNoticeNoDesc();
}
