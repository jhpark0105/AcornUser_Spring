package com.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.DashboardReservationDto;
import com.erp.process.DashboardReservationProcess;
import com.erp.response.PageResponse;
import com.erp.response.ResponseJson;
import com.erp.response.ResponseMessages;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dashboard/reservations")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardReservationController {
	
	@Autowired
	private DashboardReservationProcess dashboardReservationProcess;
	
	/**
	 * 특정 날짜의 예약 현황 데이터를 요청 페이지 범위 내에서 반환. 
	 * 
	 * @param targetDate
	 * @param pageNo
	 * @return
	 */
	@GetMapping("/date/{date}")
	public ResponseEntity<ResponseJson> getRecordsBy(
		@PathVariable("date") String targetDate, 
		@RequestParam(name = "pageNo") int pageNo	
	) {
		PageResponse<DashboardReservationDto> results 
			= dashboardReservationProcess.getRecordsBy(targetDate, pageNo);
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
	@GetMapping
	public ResponseEntity<ResponseJson> getDateInRange(
			@RequestParam("from") String startDate,
			@RequestParam("to") String endDate
	) {
		List<String> dates = dashboardReservationProcess
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
}
