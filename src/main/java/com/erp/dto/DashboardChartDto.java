package com.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dashboard 차트 출력에 필요한 데이터 전송 객체
 * ReservationRepository에서 JPQL Function을 이용하여
 * 반환한 데이터를 DTO에 저장하여 비즈니스 로직수행.  
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardChartDto {
	private String serviceName;
	private int month, count, price;
}
