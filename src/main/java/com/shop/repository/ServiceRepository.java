package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.dto.ServiceDto;
import com.shop.entity.Service;


public interface ServiceRepository extends JpaRepository<Service, String>{
	List<ServiceDto> findByServiceCode(String serviceCode);

	boolean existsById(String serviceCode);
}