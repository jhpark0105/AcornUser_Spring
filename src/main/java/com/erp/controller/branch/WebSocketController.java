package com.erp.controller.branch;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/reservation")
    @SendTo("/topic/reservations")  // 예약 관련 메시지는 이 경로로
    public String sendReservationNotification(String message) {
        return message;
    }

//    @MessageMapping("/notice")
//    @SendTo("/topic/notice")  // 공지사항 관련 메시지는 이 경로로
//    public String sendNoticeNotification(String message) {
//        return message;
//    }
}
