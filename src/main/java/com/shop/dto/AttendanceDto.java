package com.shop.dto;

import com.shop.entity.Attendance;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {

    private Integer attendanceId;
    private LocalDate attendanceDate;
    private LocalTime checkIn, checkOut;
    private String attendanceStatus;
    private String memberId, memberName; // Member의 ID만 포함

    // Entity -> DTO 변환
    public static AttendanceDto fromEntity(Attendance attendance) {
        return AttendanceDto.builder()
                .attendanceId(attendance.getAttendanceId())
                .attendanceDate(attendance.getAttendanceDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .attendanceStatus(attendance.getAttendanceStatus())
                .memberId(attendance.getMember().getMemberId()) // Member의 ID만 설정
                .memberName(attendance.getMember().getMemberName())
                .build();
    }
}