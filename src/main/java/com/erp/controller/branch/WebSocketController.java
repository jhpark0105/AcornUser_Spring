package com.erp.controller.branch;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/reservation")
    @SendTo("/topic/reservations")
    public String sendNotification(String message) {
        return message;
    }
}
