package com.erp.process.branch;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dto.CustomerDto;
import com.erp.dto.MemberDto;
import com.erp.dto.ReservationDto;
import com.erp.dto.ServiceDto;
import com.erp.entity.Customer;
import com.erp.entity.Service;
import com.erp.entity.Member;
import com.erp.entity.Reservation;
import com.erp.repository.CustomerRepository;
import com.erp.repository.MemberRepository;
import com.erp.repository.ReservationRepository;
import com.erp.repository.ServiceRepository;

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
	
	//전체 자료 읽기
	public List<Reservation> getData(){
		return reservationRepository.findAll();
	}

	//예약 리스트 조회 (0:현황 , 1:완료, 2:취소)
	//예약 현황 리스트 조회 ( reservation_status = 0)
//	public List<ReservationDto> getReservationsWithStatusZero() {
//		List<Reservation> reservations = reservationRepository.findReservationsWithStatusZero();
//		return reservations.stream()
//				.map(ReservationDto::fromEntity)
//				.collect(Collectors.toList());
//	}
	public List<Reservation> getReservationsWithStatusZero() {
		return reservationRepository.findReservationsWithStatusZero();
	}

	//예약 완료 리스트 조회 ( reservation_status = 1)
//	public List<ReservationDto> getReservationsWithStatusOne() {
//		List<Reservation> reservations = reservationRepository.findReservationsWithStatusNoOne();
//		return reservations.stream()
//				.map(ReservationDto::fromEntity)
//				.collect(Collectors.toList());
//	}
	public List<Reservation> getReservationsWithStatusOne() {
		return reservationRepository.findReservationsWithStatusNoOne();
	}

	//예약 취소 리스트 조회 ( reservation_status = 2)
//	public List<ReservationDto> getReservationsWithStatusTwo() {
//		List<Reservation> reservations = reservationRepository.findReservationsWithStatusTwo();
//		return reservations.stream()
//				.map(ReservationDto::fromEntity)
//				.collect(Collectors.toList());
//	}
	public List<Reservation> getReservationsWithStatusTwo() {
		return reservationRepository.findReservationsWithStatusTwo();
	}

	
	// 고객 데이터 조회
	public List<CustomerDto> getCustomerData() {
	    List<Customer> customers = customerRepository.findAll();
	    return customers.stream()
	            .map(CustomerDto::fromEntity)  // Customer 엔티티를 CustomerDto로 변환
	            .collect(Collectors.toList());
	}

	// 서비스 데이터 조회
	public List<ServiceDto> getServiceData() {
	    List<Service> services = serviceRepository.findAll(); // findAllServices는 서비스 목록을 반환하는 메서드 예시입니다.
	    return services.stream()
	            .map(ServiceDto::fromEntity)  // Customer 엔티티를 CustomerDto로 변환
	            .collect(Collectors.toList());
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
        
        System.out.println("CustomerId: " + customerId); // 디버깅 로그 추가
        System.out.println("ServiceCode: " + serviceCode); // 디버깅 로그 추가
        System.out.println("MemberId: " + memberId); // 디버깅 로그 추가

        if (customerId == 0 || serviceCode == null || memberId == null) {
            throw new IllegalArgumentException("Invalid Customer, Service, or Member name.");
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

//        // 서비스 이용 횟수 증가
//        reservationRepository.incrementServiceCount(serviceCode);
//
//        // 멤버 이용 횟수 증가
//        reservationRepository.incrementMemberCount(memberId);
//
//        // 서비스 가격 조회
//        int servicePrice = reservationRepository.findServicePriceByCode(serviceCode);
//
//        // 고객 총 결제 금액 증가
//        reservationRepository.incrementCustomerTotal(customerId, servicePrice);
        
        return "isSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "추가 작업 오류 : " + e.getMessage();
		}
        
    }
	
	
	//삭제
//	@Transactional
//	public String delete(int reservationNo) {
//		try {
//            //id 조회
//			int customerId = reservationRepository.findCustomerIdByReservationNo(reservationNo);
//            String serviceCode = reservationRepository.findServiceCodeByReservationNo(reservationNo);
//            String memberId = reservationRepository.findMemberIdByReservationNo(reservationNo);
//
//            //서비스 가격 조회
//            int servicePrice = reservationRepository.findServicePriceByCode(serviceCode);
//
//            //예약 삭제
//            reservationRepository.deleteById(reservationNo);
//
//            //실적 삭제
//            reservationRepository.decrementServiceCount(serviceCode);
//            reservationRepository.decrementMemberCount(memberId);
//            reservationRepository.decrementCustomerTotal(customerId, servicePrice);
//
//            return "isSuccess";
//
//
//        } catch (Exception e) {
//            return "삭제 작업 오류 : " + e.getMessage();
//        }
//	}
	
	//수정
	@Transactional
	public String update(ReservationDto reservationDto) {
	    try {
//	        // 기존 예약 정보 조회
//	    	int oldCustomerId = reservationRepository.findCustomerIdByReservationNo(reservationDto.getReservationNo());
//	        String oldServiceCode = reservationRepository.findServiceCodeByReservationNo(reservationDto.getReservationNo());
//	        String oldMemberId = reservationRepository.findMemberIdByReservationNo(reservationDto.getReservationNo());
//
//	        // 새로운 정보 조회
//	        int newCustomerId = reservationRepository.findCustomerIdByName(reservationDto.getCustomerName());
//	        String newServiceCode = reservationRepository.findServiceCodeByName(reservationDto.getServiceName());
//	        String newMemberId = reservationRepository.findMemberIdByName(reservationDto.getMemberName());
//	        int newServicePrice = reservationRepository.findServicePriceByCode(newServiceCode);
//
//	        // 기존 서비스 가격 조회
//	        int oldServicePrice = reservationRepository.findServicePriceByCode(oldServiceCode);
//
//	        // 고객 총 결제 금액 수정
//	        if (oldCustomerId != newCustomerId) {
//	            if (oldCustomerId > 0 && oldServicePrice > 0) {
//	                reservationRepository.decrementCustomerTotal(oldCustomerId, oldServicePrice);
//	            }
//	            if (newCustomerId > 0 && newServicePrice > 0) {
//	                reservationRepository.incrementCustomerTotal(newCustomerId, newServicePrice);
//	            }
//	        }
//
//	        // 서비스 이용 횟수 수정
//	        if (!oldServiceCode.equals(newServiceCode)) {
//	            if (oldServiceCode != null && !oldServiceCode.isEmpty()) {
//	                reservationRepository.decrementServiceCount(oldServiceCode);
//	            }
//	            if (newServiceCode != null && !newServiceCode.isEmpty()) {
//	                reservationRepository.incrementServiceCount(newServiceCode);
//	            }
//	        }
//
//	        // 멤버 이용 횟수 수정
//	        if (!oldMemberId.equals(newMemberId)) {
//	            if (oldMemberId != null && !oldMemberId.isEmpty()) {
//	                reservationRepository.decrementMemberCount(oldMemberId);
//	            }
//	            if (newMemberId != null && !newMemberId.isEmpty()) {
//	                reservationRepository.incrementMemberCount(newMemberId);
//	            }
//	        }
//
//	        // 예약 정보 업데이트
//	        Customer customer = new Customer();
//	        customer.setCustomerId(newCustomerId);
//
//	        Service service = new Service();
//	        service.setServiceCode(newServiceCode);
//
//	        Member member = new Member();
//	        member.setMemberId(newMemberId);

			// Name으로 ID 조회
			Integer customerId = reservationRepository.findCustomerIdByName(reservationDto.getCustomerName());
			String serviceCode = reservationRepository.findServiceCodeByName(reservationDto.getServiceName());
			String memberId = reservationRepository.findMemberIdByName(reservationDto.getMemberName());

			System.out.println("CustomerId: " + customerId); // 디버깅 로그 추가
			System.out.println("ServiceCode: " + serviceCode); // 디버깅 로그 추가
			System.out.println("MemberId: " + memberId); // 디버깅 로그 추가

			if (customerId == 0 || serviceCode == null || memberId == null) {
				throw new IllegalArgumentException("Invalid Customer, Service, or Member name.");
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
	                .customer(customer)
	                .service(service)
	                .member(member)
	                .build();

	        reservationRepository.save(reservation);

	        return "isSuccess";

	    } catch (Exception e) {
	        return "수정 작업 오류 : " + e.getMessage();
	    }
	}

	//예약 완료(확정) 상태 변경
	@Transactional
	public String reservationFinish(int reservationNo) {
		try {
			//reservationNo으로 Id 조회
			int customerId = reservationRepository.findCustomerIdByReservationNo(reservationNo);
			String serviceCode = reservationRepository.findServiceCodeByReservationNo(reservationNo);
			String memberId = reservationRepository.findMemberIdByReservationNo(reservationNo);

			// 서비스 이용 횟수 증가
        	reservationRepository.incrementServiceCount(serviceCode);

        	// 멤버 이용 횟수 증가
        	reservationRepository.incrementMemberCount(memberId);

        	// 서비스 가격 조회
        	int servicePrice = reservationRepository.findServicePriceByCode(serviceCode);

        	// 고객 총 결제 금액 증가
        	reservationRepository.incrementCustomerTotal(customerId, servicePrice);

			// 현재 상태 조회
			int reservationStatus = reservationRepository.findReservationStatusByReservationNo(reservationNo);

			// 상태가 0인 경우에만 1로 업데이트
			if (reservationStatus == 0) {
				reservationRepository.updateReservationStatusZero(reservationNo);
				return "isSuccess";
			} else {
				return "이미 확정된 예약입니다.";
			}
		} catch (Exception e) {
			return "예약 완료(확정) 작업 오류: " + e.getMessage();
		}
	}

	//예약 취소 상태 변경
	@Transactional
	public String reservationCancel(int reservationNo) {
		try {
			// 현재 상태 조회
			int reservationStatus = reservationRepository.findReservationStatusByReservationNo(reservationNo);

			// 상태가 0인 경우에만 2로 업데이트
			if (reservationStatus == 0) {
				reservationRepository.updateReservationStatusTwo(reservationNo);
				return "isSuccess";
			} else {
				return "이미 확정된 예약입니다.";
			}
		} catch (Exception e) {
			return "예약 취소 작업 오류: " + e.getMessage();
		}
	}

}
