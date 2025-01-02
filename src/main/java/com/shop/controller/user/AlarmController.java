package com.shop.controller.user;

import com.shop.entity.Alarm;
import com.shop.process.user.AlarmProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class AlarmController {
    @Autowired
    private AlarmProcess alarmProcess;

    // 모든 알림 조회
    @GetMapping
    public ResponseEntity<List<Alarm>> getAllNotifications() {
        List<Alarm> notifications = alarmProcess.getAllAlarms();
        return ResponseEntity.ok(notifications);
    }

    // 읽지 않은 알림 조회
    @GetMapping("/unread")
    public ResponseEntity<List<Alarm>> getUnreadNotifications() {
        List<Alarm> unreadNotifications = alarmProcess.getUnreadAlarms();
        return ResponseEntity.ok(unreadNotifications);
    }

    // 특정 알림 읽음 처리
    @PostMapping("/{alarmId}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long alarmId) {
        alarmProcess.markAlarmAsRead(alarmId);
        return ResponseEntity.ok().build();
    }

    // 모든 알림 읽음 처리
    @PostMapping("/read")
    public ResponseEntity<Void> markAllNotificationsAsRead() {
        alarmProcess.markAllAlarmsAsRead();
        return ResponseEntity.ok().build();
    }
}
