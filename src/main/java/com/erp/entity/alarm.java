package com.erp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class alarm {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String content; // 알림 내용

        @Column(name = "alarm_check")
        private boolean alarmCheck; // 알림 확인 여부

        @Column(name = "notice_no")
        private Long noticeNo; // 공지 번호

        @Column(name = "orders_no")
        private Long ordersNo; // 주문 번호

        @Column(name = "branch_code")
        private String branchCode; // 지점 코드

    }
