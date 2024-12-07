package com.erp.controller.branch;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    // 예약 목록 조회
    @GetMapping("/reservation")
    public Object getList() {
        return reservationProcess.getData();
    }
//    @GetMapping("/reservation")
//    public List<ReservationDto> getList(){
//    	return reservationProcess.getData();
//    }

    // 예약 추가
    @PostMapping("/reservation")
    public Map<String, Object> insertData(@RequestBody ReservationDto reservationDto) {
        reservationProcess.insertReservation(reservationDto);
        Map<String, Object> map = new HashMap<>();
        System.out.println("Received Reservation: " + reservationDto);
        map.put("isSuccess", true);
        return map;
    }

    // 예약 삭제
    @DeleteMapping("/reservation/{reservationNo}")
    public Map<String, Object> deleteData(@PathVariable("reservationNo") int reservationNo) {
        reservationProcess.delete(reservationNo);
        return Map.of("isSuccess", true);
    }

    // 예약 수정
    @PutMapping("/reservation/{reservationNo}")
    public Map<String, Object> updateData(@PathVariable("reservationNo") int reservationNo, @RequestBody ReservationDto reservationDto) {
        reservationDto.setReservationNo(reservationNo);
        reservationProcess.update(reservationDto);
        return Map.of("isSuccess", true);
    }
//    @PutMapping("/reservation/{reservationNo}")
//    public Map<String, Object> updateData(@RequestBody ReservationDto reservationDto){
//    	reservationProcess.update(reservationDto);
//    	return Map.of("isSuccess",true);
//    }
}
