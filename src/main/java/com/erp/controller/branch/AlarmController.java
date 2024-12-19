package com.erp.controller.branch;

import com.erp.entity.alarm;
import com.erp.process.branch.AlarmProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class AlarmController {
    @Autowired
    private AlarmProcess alarmProcess;

    @GetMapping
    public ResponseEntity<List<alarm>> getNotifications() {
        // AlarmProcess를 사용하여 DB에서 알림 데이터를 가져옴
        List<alarm> notifications = alarmProcess.getAllAlarms();

        // 알림 데이터를 ResponseEntity로 반환
        return ResponseEntity.ok(notifications);
    }
//    @GetMapping 
//    public ResponseEntity<?> getNotifications() {
//        // 알림 데이터를 반환하는 로직 구현
//        // 가령, DB에서 데이터를 가져오거나 임시 데이터를 반환
//        return ResponseEntity.ok(List.of("Notification 1", "Notification 2"));
//    }
}