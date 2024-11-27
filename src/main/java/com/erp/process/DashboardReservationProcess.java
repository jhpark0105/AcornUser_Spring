package com.erp.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.erp.dto.DashboardReservationDto;
import com.erp.entity.Reservation;
import com.erp.repository.DashboardReservationRepository;
import com.erp.response.PageResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 대시보드 내 예약 현황에 관한 서비스 클래스. 
 * 협의에 따라 하나의 Dashboard 서비스에 병합될 수도 있으므로 이 코드는 임시 코드.
 * 
 */
@Service
@Slf4j
public class DashboardReservationProcess {
	
	private final int PAGE_SIZE = 5; // 한 페이지의 사이즈.
	private final int BLOCK_NUMS = 3; // 페이지네이션 요소에 보일 블록 수
	
	@Autowired
	private DashboardReservationRepository dashboardReservationRepository;
	
	/**
	 * 특정 날짜에 해당하는 예약 시간, 서비스명, 고객명, 직원명이 하나의 레코드인 데이터들에 대해, 
	 * 사용자가 요청한 페이지에 해당하는 데이터들만 반환.
	 * 
	 * @param targetDate
	 * @param pageNo
	 * @return 페이지네이션 기능을 위한 페이지 시작 블록 번호, 끝 번호의 정보도 Page<DTO>와 더불어 첨가.
	 */
	public PageResponse<DashboardReservationDto> getRecordsBy(String targetDate, int pageNo) {
		Pageable pageRequest = PageRequest.of(pageNo - 1, PAGE_SIZE);
		
		Page<Reservation> pageEntities 
			= dashboardReservationRepository.findByReservationDate(targetDate, pageRequest);
		
		// Page.map(dto :: toDto) 방식을 이용하여 바로 Page<Entity> -> Page<DTO>로 변환 가능
		Page<DashboardReservationDto> pageDtos 
			= pageEntities.map(DashboardReservationDto :: toDto);
		return new PageResponse<>(pageDtos, BLOCK_NUMS);
		
	}
	
	/**
	 * 시작일과 마지막일 사이에 존재하는 날짜들을 문자열의 리스트 형태로 반환. 
	 * 
	 * @param startDate - yyyy-mm-dd ex) "2024-10-01"
	 * @param endDate - yyyy-mm-dd ex) "2024-10-31"
	 * @return - 예) ["2024-10-01", "2024-10-15", ...]
	 */
	public List<String> getDatesInRange(String startDate, String endDate) {
		Sort orderBy = Sort.by(Order.asc("reservationDate"));
		List<String> result = dashboardReservationRepository
				.findByReservationDateBetween(startDate, endDate, orderBy);
		log.info(result.toString());
		return result;
	}
}
