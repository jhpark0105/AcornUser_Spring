package com.erp.repository;

import com.erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
   // List<Attendance> findByMemberMemberId(Long memberId);

    // 특정 직원의 특정 날짜 근태 기록 조회
    List<Attendance> findByMemberMemberIdAndAttendanceDate(String memberId, LocalDate attendanceDate);

    // 특정 직원의 모든 근태 기록 조회
    List<Attendance> findByMemberMemberId(String memberId);

    // 특정 날짜의 모든 근태 기록 조회
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);
}
