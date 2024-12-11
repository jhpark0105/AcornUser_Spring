package com.erp.process.branch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.erp.dto.ServiceDto;
import com.erp.entity.Service;
import com.erp.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceProcess {
    @Autowired
    private ServiceRepository serviceRepository;

    // service 모두 읽기
    public List<ServiceDto> getDataAll() {
        List<Service> services = serviceRepository.findAll();
        return services.stream()
                       .map(service -> new ServiceDto(service.getServiceCode(), service.getServiceName(), service.getServicePrice()))
                       .collect(Collectors.toList());
    }

    // service 등록
    public String insert(ServiceDto serviceDto) {
    	// DTO -> 엔티티
        Service service = new Service(serviceDto.getServiceCode(), serviceDto.getServiceName(), serviceDto.getServicePrice());
        
        serviceRepository.save(service);
        return "isSuccess";
    }

    // service 수정 & 삭제를 위한 레코드 읽기
    public List<ServiceDto> getData(String serviceCode) {
        List<ServiceDto> services = serviceRepository.findByServiceCode(serviceCode);
        return services.stream()
                       .map(service -> new ServiceDto(service.getServiceCode(), service.getServiceName(), service.getServicePrice()))
                       .collect(Collectors.toList());
    }

    // service 수정
    public String update(ServiceDto serviceDto) {
        // DTO -> 엔티티
        Service service = new Service(serviceDto.getServiceCode(), serviceDto.getServiceName(), serviceDto.getServicePrice());
        serviceRepository.save(service);
        return "isSuccess";
    }
    
    // service 삭제
    public String delete(String serviceCode) {
        try {
            serviceRepository.deleteById(serviceCode);
            return "isSuccess";
        } catch (Exception e) {
            return "삭제 작업 오류 : " + e.getMessage();
        }
    }
}