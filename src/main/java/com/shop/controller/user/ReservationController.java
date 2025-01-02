package com.shop.controller.user;

import com.shop.dto.CustomerDto;
import com.shop.dto.MemberDto;
import com.shop.dto.ReservationDto;
import com.shop.dto.ServiceDto;
import com.shop.entity.Alarm;
import com.shop.entity.Customer;
import com.shop.process.user.AlarmProcess;
import com.shop.process.user.CustomerLoginProcess;
import com.shop.process.user.ReservationProcess;
import com.shop.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") //React 서버 주소
public class ReservationController {
    @Autowired
    private CustomerLoginProcess customerLoginProcess;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    private AlarmProcess alarmProcess;

    @Autowired
    private ReservationProcess reservationProcess;

    @Autowired
    private JwtProvider jwtProvider;

    // 쿠키에서 customerShopid 추출하여 customer 조회
    @GetMapping("/reservation/customer/username")
    public CustomerDto getCustomerByToken(@CookieValue(name = "accessToken") String token) {
        // 토큰에서 adminId(id) 추출
        System.out.println("Received Token: " + token);
        String customerShopid = jwtProvider.validate(token);
        System.out.println("Extracted customerShopid: " + customerShopid);

        // 추출한 customerShopid로 customer 조회
        Customer customer = customerLoginProcess.findOne(customerShopid);
        return CustomerDto.toDto(customer);
    }

    // 서비스 목록 조회
    @GetMapping("/reservation/service/user")
    public List<ServiceDto> getServiceList() {
        return reservationProcess.getServiceData();  // 서비스 목록을 반환
    }

    // 고객 목록 조회
    @GetMapping("/reservation/customer/user")
    public List<CustomerDto> getCustomerData() {
        return reservationProcess.getCustomerData();  // 고객 목록을 반환
    }

    // 직원 목록 조회
    @GetMapping("/reservation/member/user")
    public List<MemberDto> getMemberList() {
        return reservationProcess.getAllMembers();  // 직원 목록을 반환
    }

    // 예약 추가
    @PostMapping("/reservation/user")
    public Map<String, Object> insertData(@RequestBody ReservationDto reservationDto) {
        // 예약 처리
        reservationProcess.insertReservation(reservationDto);

        // 고객명을 가져오기
        String customerName = reservationDto.getCustomerName();
        String memberName = reservationDto.getMemberName();
        String reservationDate = reservationDto.getReservationDate();
        String reservationTime = reservationDto.getReservationTime();

//        // 알림 메시지 생성
//        String alarmContent = customerName + "님의 예약이 등록되었습니다!";
        // 알림 메시지 생성
        String alarmContent = customerName + "님의 예약이 등록되었습니다!\n"
                + "담당 디자이너: " + memberName + "\n"
                + "예약 일시: " + reservationDate +" "+ reservationTime;
        // 알림 생성 및 DB 저장
        Alarm alarm = com.shop.entity.Alarm.builder()
                .content(alarmContent)
                .build();

        alarmProcess.saveAlarm(alarm);

        // WebSocket으로 알림 전송
        messagingTemplate.convertAndSend("/topic/reservations",
                alarmContent);


        // 응답 데이터 생성
        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", true);
        return map;
    }
}
