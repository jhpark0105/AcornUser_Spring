package com.shop.process.user;

import com.shop.dto.CustomerDto;
import com.shop.dto.MemberDto;
import com.shop.dto.ReservationDto;
import com.shop.dto.ServiceDto;
import com.shop.entity.Customer;
import com.shop.entity.Member;
import com.shop.entity.Service;
import com.shop.entity.Reservation;
import com.shop.repository.CustomerRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.ReservationRepository;
import com.shop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ReservationProcess {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private MemberRepository memberRepository;

    // 고객 데이터 조회
    public List<CustomerDto> getCustomerData() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerDto::fromEntity)  // Customer 엔티티를 CustomerDto로 변환
                .collect(Collectors.toList());
    }

    // 서비스 데이터 조회
//    public List<ServiceDto> getServiceData() {
//        List<com.shop.entity.Service> services = serviceRepository.findAll(); // findAllServices는 서비스 목록을 반환하는 메서드 예시입니다.
//        return services.stream()
//                .map(ServiceDto::fromEntity)  // Customer 엔티티를 CustomerDto로 변환
//                .collect(Collectors.toList());
//    }
    public List<ServiceDto> getServiceData() {
        return serviceRepository.findServiceDetails(); // ServiceDto 리스트를 바로 반환
    }

    // 모든 멤버를 조회하고 MemberDto로 변환하여 반환
    public List<MemberDto> getAllMembers() {
        List<Member> members = memberRepository.findAll(); // Repository에서 멤버 리스트 가져오기
        return members.stream()
                .map(MemberDto::fromEntity) // Member를 MemberDto로 변환
                .collect(Collectors.toList()); // 리스트로 변환
    }


    //예약 등록
    @Transactional
    public String insertReservation(ReservationDto reservationDto) {
        try {
            // Name으로 ID 조회
            Integer customerId = reservationRepository.findCustomerIdByName(reservationDto.getCustomerName());
            String serviceCode = reservationRepository.findServiceCodeByName(reservationDto.getServiceName());
            String memberId = reservationRepository.findMemberIdByName(reservationDto.getMemberName());

            // 로그 추가
            System.out.println("CustomerId: " + customerId);
            System.out.println("ServiceCode: " + serviceCode);
            System.out.println("MemberId: " + memberId);

            // 예외 처리 추가: customerId, serviceCode, memberId가 null일 경우
            if (customerId == null || serviceCode == null || memberId == null) {
                throw new IllegalArgumentException("Invalid Customer, Service, or Member name. Please check inputs.");
            }

            // 예약 정보 저장
            Customer customer = new Customer();
            customer.setCustomerId(customerId);

            Service service = new Service();
            service.setServiceCode(serviceCode);

            Member member = new Member();
            member.setMemberId(memberId);

            Reservation reservation = Reservation.builder()
                    .reservationNo(reservationDto.getReservationNo())
                    .reservationDate(reservationDto.getReservationDate())
                    .reservationTime(reservationDto.getReservationTime())
                    .reservationComm(reservationDto.getReservationComm())
                    .reservationStatus(reservationDto.getReservationStatus())
                    .customer(customer)
                    .service(service)
                    .member(member)
                    .build();

            reservationRepository.save(reservation);

            return "isSuccess";
        } catch (IllegalArgumentException e) {
            System.err.println("Input validation failed: " + e.getMessage());
            return "추가 작업 오류 : " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "추가 작업 오류 : " + e.getMessage();
        }
    }

}
