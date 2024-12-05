package com.erp.process;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.erp.dto.DashboardChartDto;
import com.erp.dto.DashboardReservationDto;
import com.erp.dto.ProductDto;
import com.erp.entity.Reservation;
import com.erp.entity.Service;
import com.erp.repository.DashboardReservationRepository;
import com.erp.repository.DashboardServiceRepository;
import com.erp.repository.ProductRepository;
import com.erp.response.PageResponse;

@org.springframework.stereotype.Service
public class DashboardProcess {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final int PAGE_SIZE = 5; // 한 페이지의 사이즈.
	private final int BLOCK_NUMS = 3; // 페이지네이션 요소에 보일 블록 수
	
	private final ProductRepository productRepository;
	private final DashboardReservationRepository dashboardReservationRepository;
	private final DashboardServiceRepository dashboardServiceRepository;
	
	/**
	 * 비즈니스 로직에 필요한 Repository 인터페이스의 구현 클래스 인스턴스를 생성자 주입 방식으로 치환 
	 * @param productRepository
	 * @param dashboardReservationRepository
	 * @param dashboardServiceRepository
	 */
    public DashboardProcess (
    		ProductRepository productRepository, 
    		DashboardReservationRepository dashboardReservationRepository, 
    		DashboardServiceRepository dashboardServiceRepository
    ) {
        this.productRepository = productRepository;
        this.dashboardReservationRepository = dashboardReservationRepository;
        this.dashboardServiceRepository = dashboardServiceRepository;
    }
    
    /**
     * 
     * @param page
     * @return
     */
    public Page<ProductDto> getProductList(int page){
		Sort sort = Sort.by(Sort.Order.asc("productEa"));
		Pageable pageable = PageRequest.of(page, 10, sort);
	    return productRepository.getPartProductList(pageable).map(ProductDto::fromEntity);
	}
    
    
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
	
	

	/**
	 * ==================== 반환 형식 ====================
	 * series: [
	 *   {
	 *     name: "PRODUCT A",
	 *     data: [44, 55, 41, 67, 22, 43], // Y축 데이터
	 *   },
	 *   {
	 *     name: "PRODUCT B",
	 *     data: [13, 23, 20, 8, 13, 27],
	 *   },
	 *   {
	 *     name: "PRODUCT C",
	 *     data: [11, 17, 15, 15, 21, 14],
	 *   },
	 *   {
	 *     name: "PRODUCT D",
	 *     data: [21, 7, 25, 13, 22, 8],
	 *   },
	 * ],
	 * 
	 * ==================== 자료 구조 ====================
	 * List<Map<String, Object>>
	 * 	Key : "name", Value : "서비스명"
	 * 	Key : "data", Value : "매출액 저장 리스트" : List<Integer>
	 */

	/**
	 * 단일 서비스명 조회
	 * 서비스 코드가 'S0' 으로 시작하는 서비스 
	 */
	public List<Service> getSingleService() {
		return dashboardServiceRepository.findByServiceCodeContaining("S0%");
	}

	/**
	 * ========== 월별 서비스 시술 횟수 조회 ==========
	 * 	1. repository에서 예약 현황 조회 (서비스 코드로 정렬)
	 * 		복수 서비스의 경우 단일 항목으로 분할
	 * 	2. 월별 구분
	 * 		배열 인덱스 활용하여 저장
	 * 	3. 응답 객체 포장
	 * @return
	 */
	public List<Map<String, Object>> getServiceChart() {
		List<Map<String, Object>> response = new ArrayList<>();
		
		/* 1> 서비스별 월 매출을 저장할 자료구조 Map<String, Integer[]> */
		Map<String, List<Integer>> map = new LinkedHashMap<>();

		/* 2> 단일 서비스 항목으로 Map 초기화 {서비스명, 배열} */
		getSingleService().forEach(service -> {
			/* 매출액 저장용 배열 리스트 초기화 */
			List<Integer> monthList = new ArrayList<>(12);
			for (int i = 0; i < 12; i++) monthList.add(0);
			
			/* 서비스 매출액을 월별로 구분하여 저장할 배열 리스트 */
			log.info("serviceName ; {} ", service.getServiceName());
			map.put(service.getServiceName(), monthList);
		});

		/* 서비스명, 시술 월, 시술횟수, 시술 비용 DB 데이터 조회 */
		List<DashboardChartDto> list = dashboardReservationRepository.findServiceMonthCount();
		list.forEach(dto -> {
			String name = dto.getServiceName();
			int month = dto.getMonth();
			int count = dto.getCount();
			int price = dto.getPrice();
			log.info("서비스명 : {}, 시술 월 : {}, 시술 횟수 : {}, 시술 비용 : {}", 
					name, month, count, price);
			/* 복수 서비스 분할 작업 : 서비스 명에 , 구분자 포함 시 분할 후 데이터에 저장 */
			for (String serviceName : name.split(",")) {
				/* 단일 서비스명에 해당하는 매출액 리스트 */
				List<Integer> revenueList = map.get(serviceName);
				int revenue = count * price;
				int index = month - 1;
				revenueList.set(index, revenueList.get(index) + revenue);
			}
		});
		/**
		 * Map.forEach(biConsumer) -> Key, Value 두 개의 매개변수를 전달받는다.
		 * 
		 * for (Map.Entry<K, V> entry : map.entrySet())
		 *    action.accept(entry.getKey(), entry.getValue());
		 * 
		 */
		map.forEach((key, value) -> response.add(Map.of("name", key, "data", value)));
		return response;
	}

}

