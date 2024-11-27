package com.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.process.DashboardServiceProcess;

@RestController
@RequestMapping("/dashboard/service")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardServiceController {
	@Autowired
	private DashboardServiceProcess dashboardServiceProcess;

	/**
	 * [서비스 차트 출력용 조회 데이터]
	 * 	서비스별 월별 시술 횟수
	 * 	연간 서비스 매출액 통계
	 * @return List<Map<String, Object>>
	 */
	@GetMapping()
	public ResponseEntity<Object> getServiceChart() {
		return ResponseEntity.ok().body(dashboardServiceProcess.getServiceChart());
	}
}
