package com.shop.repository;

import com.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // 특정 공지사항의 댓글 목록 조회
    List<Comment> findByNoticeNoOrderByCreatedAtAsc(int noticeNo);

    // 댓글 등록 시, 가장 큰 번호를 가져오기
    @Query("SELECT COALESCE(MAX(c.commentNo), 0) FROM Comment c")
    int findMaxCommentNo();
}