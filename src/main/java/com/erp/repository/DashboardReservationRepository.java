package com.erp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.dto.DashboardChartDto;
import com.erp.entity.Reservation;

public interface DashboardReservationRepository extends JpaRepository<Reservation, Integer> {
	
	/**
	 * 특정 날짜에 해당하는 예약 현황 데이터들 중 요청한 페이지의 데이터들만 반환.
	 * 
	 * @param reservationDate
	 * @param pageRequest
	 * @return
	 */
	Page<Reservation> findByReservationDate(
			String reservationDate, Pageable pageRequest
	);
	
	/**
	 * DB 내에 사용자가 요청한 시작일과 마지막일 사이에 존재하는 날짜들의 데이터를 
	 * ['2024-10-01', '2024-10-21', ...] 형식으로 반환. 
	 * 
	 * 달력에서 이전 달, 다음 달 이동 시 언제 예약 현황이 존재하는지를 한 눈에 
	 * 파악하기 위한 용도. 
	 * 
	 * @param startDate
	 * @param endDate
	 * @param orderBy - 날짜를 오름차순으로 정렬. 
	 * (정렬 기준 부과하지 않으면 완전히 정렬되어 나오지 않는 현상 때문에 이 매개변수를 부여함. )
	 * @return
	 */
	@Query(value = """
		SELECT DISTINCT r.reservationDate 
		FROM Reservation r 
		WHERE r.reservationDate 
		BETWEEN :startDate AND :endDate""")
	List<String> findByReservationDateBetween(
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate,
			Sort orderBy
	);
	
	
	/**
	 * ========== 조회 필드 ==========
	 * 	1. 서비스명
	 * 	2. 서비스 시행 일자(MONTH) 
	 * 		FUNCTION('MONTH', Date) 
	 * 			: Date 타입에서 MONTH에 해당하는 값을 추출(반환)하는 JPA 함수 _ Native Query에 준하는 수준..
	 * 	3. 시술 횟수(COUNT) 
	 * 		FUNCTION('STR_TO_DATE', String, Format) 
	 * 			: 날짜 필드가 문자열 타입일 경우 날짜 타입으로 변경해주는 JPA 함수   "StringToDate"
	 * 	4. 시술 비용
	 * 
	 * ============ 조건 ============
	 * 	연도 일치, 서비스명 그룹, 서비스코드, 예약 날짜로 정렬
	 * 
	 * @return 차트 출력에 필요한 데이터 전송용 객체 생성 후 데이터 저장, 전달
	 */
	@Query("SELECT new com.erp.dto.DashboardChartDto(" +
			"r.service.serviceName, " +
		       	   "FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.reservationDate, '%Y-%m-%d')), " +
		       	   "COUNT(r), " +
				   "r.service.servicePrice) " + 
			"FROM Reservation r " +
			"WHERE FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.reservationDate, '%Y-%m-%d')) = FUNCTION('YEAR', CURRENT_DATE) " +
			"GROUP BY r.service.serviceName, FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.reservationDate, '%Y-%m-%d')) " +
			"ORDER BY r.service.serviceCode ASC, FUNCTION('STR_TO_DATE', r.reservationDate, '%Y-%m-%d') ASC")
	List<DashboardChartDto> findServiceMonthCount();

}
