package com.erp.process.branch;

import com.erp.entity.Alarm;
import com.erp.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlarmProcess {

    @Autowired
    private AlarmRepository alarmRepository;

    // 알림 저장
    public Alarm saveAlarm(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    // 모든 알림 조회
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    // 읽지 않은 알림 조회
    public List<Alarm> getUnreadAlarms() {
        return alarmRepository.findByIsReadFalse();
    }

    // 특정 알림 읽음 처리
    @Transactional
    public void markAlarmAsRead(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm ID: " + alarmId));
        alarm.setIsRead(true);
        alarmRepository.save(alarm);
    }

    // 모든 알림 읽음 처리
    @Transactional
    public void markAllAlarmsAsRead() {
        List<Alarm> unreadAlarms = alarmRepository.findByIsReadFalse();
        unreadAlarms.forEach(alarm -> alarm.setIsRead(true));
        alarmRepository.saveAll(unreadAlarms);
    }
}
