package com.shop.entity;

import com.shop.dto.AttendanceDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer attendanceId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "check_in")
    private LocalTime checkIn;

    @Column(name = "check_out")
    private LocalTime checkOut;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    // DTO -> Entity 변환
    public static Attendance fromDto(AttendanceDto attendanceDto, Member member) {
        return Attendance.builder()
                .attendanceId(attendanceDto.getAttendanceId())
                .attendanceDate(attendanceDto.getAttendanceDate())
                .checkIn(attendanceDto.getCheckIn())
                .checkOut(attendanceDto.getCheckOut())
                .attendanceStatus(attendanceDto.getAttendanceStatus())
                .member(member) // Member를 매개변수로 직접 설정
                .build();
    }
}
