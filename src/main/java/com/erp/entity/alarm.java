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
        @Column(name = "alarm_id")
        private Long id;


        @Column(name = "alarm_content")
        private String content; // 알림 내용

        @Column(name = "notice_no")
        private Long noticeNo; // 공지 번호

        @Column(name = "orders_no")
        private Long ordersNo; // 주문 번호

    }
