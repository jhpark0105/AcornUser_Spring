package com.erp.controller.branch;

import com.erp.entity.Member;
import com.erp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.dto.AttendanceDto;
import com.erp.process.branch.AttendanceProcess;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceProcess attendanceProcess;
    @Autowired
    private MemberRepository memberRepository;

    // 전체 근태 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        return ResponseEntity.ok(attendanceProcess.getAllList());
    }

    // 특정 날짜 근태 조회
    @GetMapping("/by-date")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByDate(
            @RequestParam String memberId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(
                attendanceProcess.getAttendanceByDate(memberId, localDate)
                        .stream()
                        .map(AttendanceDto::fromEntity)
                        .toList()
        );
    }

    // 출근 처리
    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(
            @RequestParam String memberId,
            @RequestParam String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다."));

        if (!member.getMemberName().equals(password)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        attendanceProcess.checkIn(memberId);
        return ResponseEntity.ok("출근 처리 완료");
    }

    // 퇴근 처리
    @PostMapping("/check-out")
    public ResponseEntity<String> checkOut(@RequestParam String memberId) {
        attendanceProcess.checkOut(memberId);
        return ResponseEntity.ok("퇴근 처리 완료");
    }

    // 근태 추가
    @PostMapping("/add")
    public ResponseEntity<?> addAttendance(@RequestBody AttendanceDto attendanceDto) {
        return ResponseEntity.ok(attendanceProcess.insert(attendanceDto));
    }

    // 근태 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        return ResponseEntity.ok(attendanceProcess.update(attendanceDto));
    }

    // 근태 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Integer id) {
        return ResponseEntity.ok(attendanceProcess.delete(id));
    }
}
