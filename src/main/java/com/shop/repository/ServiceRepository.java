package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.dto.ServiceDto;
import com.shop.entity.Service;
import org.springframework.data.jpa.repository.Query;


public interface ServiceRepository extends JpaRepository<Service, String>{
	List<ServiceDto> findByServiceCode(String serviceCode);

	boolean existsById(String serviceCode);

//	@Query("select serviceCode, serviceName, serviceCnt, servicePrice from Service")
//	List<Service> findServiceDetails();
@Query("select new com.shop.dto.ServiceDto(s.serviceCode, s.serviceName, s.servicePrice) from Service s")
List<ServiceDto> findServiceDetails();
}