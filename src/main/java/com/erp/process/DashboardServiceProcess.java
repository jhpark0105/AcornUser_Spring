package com.erp.process;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.entity.Service;
import com.erp.repository.DashboardReservationRepository;
import com.erp.repository.DashboardServiceRepository;

@org.springframework.stereotype.Service
public class DashboardServiceProcess {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DashboardReservationRepository dashboardReservationRepository;
	@Autowired
	private DashboardServiceRepository dashboardServiceRepository;

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
		getSingleService().stream().forEach((service) -> {
			/* 매출액 저장용 배열 리스트 초기화 */
			List<Integer> monthList = new ArrayList<>(12);
			for (int i = 0; i < 12; i++) monthList.add(0);
			
			/* 서비스 매출액을 월별로 구분하여 저장할 배열 리스트 */
			log.info("serviceName ; {} ", service.getServiceName());
			map.put(service.getServiceName(), monthList);
		});

		/* 서비스명, 시술 월, 시술횟수, 시술 비용 DB 데이터 조회 */
		List<Object[]> list = dashboardReservationRepository.findServiceMonthCount();
		for (Object[] obj : list) {
			String name = String.valueOf(obj[0]);
			int month = Integer.parseInt(String.valueOf(obj[1]));
			int count = Integer.parseInt(String.valueOf(obj[2]));
			int price =  Integer.parseInt(String.valueOf(obj[3]));
			log.info("서비스명 : {}, "
					+ "시술 월 : {}, "
					+ "시술 횟수 : {}, "
					+ "시술 비용 : {}", 
					name, month, count, price);
			
			/* 복수 서비스 분할 작업 : 서비스 명에 , 구분자 포함 시 분할 후 데이터에 저장 */
			for (String serviceName : name.split(",")) {
				
				int beforeRevenue = map.get(serviceName).get(month);
				int revenue = count * price;
				map.get(serviceName).set(month-1, beforeRevenue + revenue);
			}
		}
		/* Key, Value로 포장 */
		for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
			response.add(Map.of("name", entry.getKey(), "data", entry.getValue()));
		}
		return response;
	}

}
