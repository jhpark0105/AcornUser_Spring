package com.shop.process.user;

import com.shop.dto.CommentDto;
import com.shop.entity.Comment;
import com.shop.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentProcess {

    @Autowired
    private CommentRepository commentRepository;

    // 댓글 트리 구조 생성
    public List<CommentDto> getCommentsTreeByNotice(int noticeNo) {
        List<Comment> comments = commentRepository.findByNoticeNoOrderByCreatedAtAsc(noticeNo);

        // DTO로 변환
        Map<Integer, CommentDto> commentMap = comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toMap(CommentDto::getCommentNo, dto -> {
                    dto.setReplies(new ArrayList<>()); // replies 초기화
                    return dto;
                }));

        List<CommentDto> rootComments = new ArrayList<>();

        for (CommentDto dto : commentMap.values()) {
            if (dto.getParentNo() == null) {
                rootComments.add(dto);
            } else {
                CommentDto parent = commentMap.get(dto.getParentNo());
                if (parent != null) {
                    parent.addReply(dto);
                }
            }
        }

        return rootComments;
    }

    // 특정 댓글을 DB에서 조회
    private Comment getCommentById(int commentNo) {
        return commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentNo));
    }

    // 특정 댓글 조회 (DTO 변환 포함)
    public CommentDto getCommentDtoById(int commentNo) {
        Comment comment = getCommentById(commentNo);
        return CommentDto.fromEntity(comment);
    }

    // 댓글 작성 및 답글 작성
    public CommentDto createComment(CommentDto commentDto) {
        if (commentDto.getParentNo() != null) {
            boolean parentExists = commentRepository.existsById(commentDto.getParentNo());
            if (!parentExists) {
                throw new IllegalArgumentException("Parent comment does not exist.");
            }
        }

        Comment comment = commentDto.toEntity();
        int nextCommentNo = commentRepository.findMaxCommentNo() + 1;
        comment.setCommentNo(nextCommentNo);

        Comment savedComment = commentRepository.save(comment);
        return CommentDto.fromEntity(savedComment);
    }

    // 댓글 수정
    public CommentDto updateComment(int commentNo, String updatedContent) {
        Comment comment = getCommentById(commentNo); //수정 대상 댓글 조회
        comment.setContent(updatedContent);
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(comment);
        return CommentDto.fromEntity(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(int commentNo) {
        if (!commentRepository.existsById(commentNo)) {
            throw new IllegalArgumentException("Comment not found with id: " + commentNo);
        }
        commentRepository.deleteById(commentNo);
    }
}