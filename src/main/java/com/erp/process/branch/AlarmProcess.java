package com.erp.process.branch;

import com.erp.entity.alarm;
import com.erp.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmProcess {

    @Autowired
    private AlarmRepository alarmRepository;

    // 알림 저장
    public alarm saveAlarm(alarm alarm) {
        return alarmRepository.save(alarm);
    }
}
