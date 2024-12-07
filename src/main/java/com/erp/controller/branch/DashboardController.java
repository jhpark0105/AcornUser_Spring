package com.erp.controller.branch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.DashboardReservationDto;
import com.erp.dto.NoticeDto;
import com.erp.dto.ProductDto;
import com.erp.process.branch.DashboardProcess;
import com.erp.process.branch.NoticeProcess;
import com.erp.response.PageResponse;
import com.erp.response.ResponseJson;
import com.erp.response.ResponseMessages;

@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {
	private final Logger log = LoggerFactory.getLogger(getClass()); 

    private final NoticeProcess noticeProcess;
    private final DashboardProcess dashboardProcess;

	public DashboardController(NoticeProcess noticeProcess, DashboardProcess dashboardProcess) {
		this.noticeProcess = noticeProcess;
		this.dashboardProcess = dashboardProcess;
	}
	
	/**
	 * 주요 공지 목록 출력
	 * @return 
	 */
	@GetMapping(path = "/notice")
	public List<NoticeDto> getCheckedNoticeList(){
		return noticeProcess.getCheckedNoticeList();
	}

	/**
	 * 특정 날짜의 예약 현황 데이터를 요청 페이지 범위 내에서 반환. 
	 * 
	 * @param targetDate
	 * @param pageNo
	 * @return
	 */
	@GetMapping("reservations/date/{date}")
	public ResponseEntity<ResponseJson> getRecordsBy(
		@PathVariable("date") String targetDate, 
		@RequestParam(name = "pageNo") int pageNo	
	) {
		PageResponse<DashboardReservationDto> results 
			= dashboardProcess.getRecordsBy(targetDate, pageNo);
		ResponseJson response = null;
		
		// 데이터 조회 상태에 따라 응답 상태 코드(status) 및 상태 메시지 결정하여 반환.
		String message = null;
		
		// 데이터 조회 요청 자체는 성공했으나 조회된 데이터가 없을 경우.
		// Page.getNumberOfElements() : 현재 조회한 Page의 사이즈
		if (results.getPages().getNumberOfElements() == 0) {
			message = ResponseMessages.NO_DATA;
		} else {
			// 데이터 조회 요청 성공 및 조회된 데이터가 존재할 경우.
			message = ResponseMessages.READ_SUCCESS;
		}
		
		response = ResponseJson.builder()
				.status(HttpStatus.OK)
				.message(message)
				.data(results)
				.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	
	/**
	 * 시작일과 마지막일 사이에 DB에 존재하는 날짜들만을 ['2024-10-05', '2024-10-12', ...] 
	 * 형태로 반환. 
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping("/reservations")
	public ResponseEntity<ResponseJson> getDateInRange(
			@RequestParam("from") String startDate,
			@RequestParam("to") String endDate
	) {
		List<String> dates = dashboardProcess
				.getDatesInRange(startDate, endDate);
		
		String message = null;
		
		if (dates == null || dates.size() == 0) {
			message = ResponseMessages.NO_DATA;
		} else {
			message = ResponseMessages.READ_SUCCESS;
		}
		
		ResponseJson response = ResponseJson.builder()
				.status(HttpStatus.OK)
				.message(message)
				.data(dates)
				.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}
	

	/**
	 * [서비스 차트 출력용 조회 데이터]
	 * 	서비스별 월별 시술 횟수
	 * 	연간 서비스 매출액 통계
	 * @return List<Map<String, Object>>
	 */
	@GetMapping(path = "/service/chart")
	public ResponseEntity<Object> getServiceChart() {
		return ResponseEntity.ok().body(dashboardProcess.getServiceChart());
	}
}
