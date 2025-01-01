package com.shop.repository;

import com.shop.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByIsReadFalse(); // 읽지 않은 알림 조회
}
