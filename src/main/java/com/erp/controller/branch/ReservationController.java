package com.erp.controller.branch;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.CustomerDto;
import com.erp.dto.MemberDto;
import com.erp.dto.ReservationDto;
import com.erp.dto.ServiceDto;
import com.erp.process.branch.ReservationProcess;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 서버 주소
public class ReservationController {
	@Autowired
	private ReservationProcess reservationProcess;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    // 서비스 목록 조회
    @GetMapping("/reservation/service")
    public List<ServiceDto> getServiceList() {
        return reservationProcess.getServiceData();  // 서비스 목록을 반환
    }

    // 고객 목록 조회
    @GetMapping("/reservation/customer")
    public List<CustomerDto> getCustomerData() {
        return reservationProcess.getCustomerData();  // 고객 목록을 반환
    }

    // 직원 목록 조회
    @GetMapping("/reservation/member")
    public List<MemberDto> getMemberList() {
        return reservationProcess.getAllMembers();  // 직원 목록을 반환
    }

    // 예약 전체 목록 조회
    @GetMapping("/reservation")
    public Object getList() {
        return reservationProcess.getData();
    }

    //예약 현황 리스트 조회
    @GetMapping("/reservation/confirm")
    public List<ReservationDto> getConfirmList() {
        return reservationProcess.getReservationsWithStatusZero();
    }

    //예약 완료 리스트 조회
    @GetMapping("/reservation/finish")
    public List<ReservationDto> getFinishList() {
        return reservationProcess.getReservationsWithStatusOne();
    }

    //예약 취소 리스트 조회
    @GetMapping("/reservation/cancel")
    public List<ReservationDto> getCancelList() {
        return reservationProcess.getReservationsWithStatusTwo();
    }

    // 예약 추가
    @PostMapping("/reservation")
    public Map<String, Object> insertData(@RequestBody ReservationDto reservationDto) {
        reservationProcess.insertReservation(reservationDto);

        // WebSocket으로 알림 전송
        messagingTemplate.convertAndSend("/topic/reservations", "새로운 예약이 등록되었습니다!");

        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", true);
        return map;
    }

    // 예약 삭제
//    @DeleteMapping("/reservation/{reservationNo}")
//    public Map<String, Object> deleteData(@PathVariable("reservationNo") int reservationNo) {
//        reservationProcess.delete(reservationNo);
//        return Map.of("isSuccess", true);
//    }

    // 예약 수정
    @PutMapping("/reservation/{reservationNo}")
    public Map<String, Object> updateData(@PathVariable("reservationNo") int reservationNo, @RequestBody ReservationDto reservationDto) {
        reservationDto.setReservationNo(reservationNo);
        reservationProcess.update(reservationDto);
        return Map.of("isSuccess", true);
    }


    // 예약 완료(확정) 상태 수정
    @PutMapping("/reservation/{reservationNo}/finish")
    public Map<String, Object> finishReservation(@PathVariable("reservationNo") int reservationNo, @RequestBody ReservationDto reservationDto) {
            // 예약 번호 설정
            reservationDto.setReservationNo(reservationNo);

            // 예약 상태 완료 처리
            reservationProcess.reservationFinish(reservationDto);

            // 성공 응답 반환
            return Map.of("isSuccess", true);
    }
}
