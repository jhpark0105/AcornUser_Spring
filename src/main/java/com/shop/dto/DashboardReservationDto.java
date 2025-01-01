package com.shop.dto;

import com.shop.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dashboard에서, 캘린더 내 특정 날짜 클릭 시 해당 날짜의 예약현황 목록이 
 * 나오게끔 하기 위해 필요한 정보들을 전송하기 위한 DTO 클래스.
 * 
 * 참조하는 클래스
 * Reservation (Entity) - Reservation 내부에 
 * Customer, Service, Member 객체 타입으로 선언된 필드들에 의존함.
 * 
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardReservationDto {
	
	// 레코드를 구분하기 위한 용도
	private int reservationNo;
	
	// Reservation Entity에 존재하는 필드
	//private LocalDateTime reservationDate;
	//private LocalDateTime reservationTime;
	
	// 날짜에서 불필요한 시간 정보까지 나오지 않도록 
	// 날짜와 시간 정보를 따로 분리하여 날짜 또는 시간만 볼 수 있도록 함.
	private String reservationDate;
	private String reservationTime;
	
	/*
	 * 원래는 serviceName 등 이름의 정보만으로도 충분하나 
	 * 혹시 모를 나중의 기능을 위해 생성함.
	 * 예) 직원명을 클릭하면 해당 직원의 정보를 보여주는 기능 등.
	 * 사실 이 경우 ID 필드와 name 필드만 필요하나, 이 필드만을 담는 
	 * DTO를 따로 정의하는게 맞는지 아직 잘 몰라서 일단 기존 DTO를 사용하기로 함.
	 * ------
	 * Conflict)
	 * 엔티티 내 모든 필드 정보들을 그대로 담는 기존 DTO 사용 시 우려될 점)
	 * - 클라이언트 단에 노출되지 않아도 되는 다른 데이터들도 노출될 수 있어 보안적 우려.
	 * - 네트워크 트래픽 부담을 조금이라도 줄일려면 사소해보여도 불필요한 필드 정보는 뺴는 게 좋을 것 같음. 
	 * 대용량 데이터를 서버에서 클라이언트로 전송해야할 경우 트래픽 부담 증가 및 응답 속도 저하가 예상됨. 
	 * 
	 * 엔티티 내 id, name 필드 정보만 담는 새 DTO 사용 시 우려될 점)
	 * - DTO의 양이 많아져 프로젝트 구조가 조금 복잡해질 수 있다. 
	 * - 지금은 불필요하다 생각했지만 나중에 알고보니 다른 필드의 정보가 필요할 수도 있다. 
	 * 이 경우 따로 해당 정보를 JpaRepository를 거쳐 영속성 컨텍스트에 추가적으로 요청해야 할 수 있는데, 
	 * 이렇게 하기 보단 애초에 모든 필드가 존재하는 기존 DTO를 사용하는 게 더 효율적일 수 있음.
	 * 
	 */
	/*
	private MemberDto member;
	private CustomerDto customer;
	private ServiceDto service;
	*/
	
	// 추가 기능 존재 여부에 대한 생각은 일단 미루고 이름만 가져오기
	private String memberName;
	private String customerName;
	private String serviceName;
	
	/**
	 * Reservation Entity로부터 필요한 정보를 추출하여 이를 DTO로 변환.
	 * 
	 * @param entity
	 * @return
	 */
	public static DashboardReservationDto toDto(Reservation entity) {
		return DashboardReservationDto.builder()
				.reservationNo(entity.getReservationNo())
				.reservationDate(entity.getReservationDate())
				.reservationTime(entity.getReservationTime())
				.memberName(entity.getMember().getMemberName())
				.customerName(entity.getCustomer().getCustomerName())
				.serviceName(entity.getService().getServiceName())
				.build();
	}
}
