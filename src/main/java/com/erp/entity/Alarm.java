package com.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "alarm_id")
        private Long id; // 알림 ID

        @Column(name = "alarm_content", nullable = false)
        private String content; // 알림 내용

        @Column(name = "notice_no")
        private Long noticeNo; // 공지 번호

        @Column(name = "orders_no")
        private Long ordersNo; // 주문 번호

        @Column(name = "is_read", nullable = false)
        private Boolean isRead; // 읽음 여부 (기본값: false)

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt; // 알림 생성 시간

        @PrePersist
        protected void onCreate() {
                this.createdAt = LocalDateTime.now(); // 엔티티 저장 시 생성 시간 자동 설정
                if (this.isRead == null) {
                        this.isRead = false; // 기본값으로 읽지 않음 설정
                }
        }
}
