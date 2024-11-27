package com.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.entity.Service;

public interface DashboardServiceRepository extends JpaRepository<Service, String>{

	/**
	 * 단일 서비스명 조회
	 * @param serviceCode
	 * @return
	 */
	 @Query("SELECT s FROM Service s WHERE s.serviceCode LIKE :serviceCode")
	List<Service> findByServiceCodeContaining (@Param("serviceCode") String code);
}
