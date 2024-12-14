package com.erp.repository;

import com.erp.entity.alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<alarm, Long> {

}
