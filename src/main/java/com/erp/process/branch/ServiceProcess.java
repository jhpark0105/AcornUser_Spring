package com.erp.process.branch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> response = new HashMap<>();
        try {
            // 서비스 코드 중복 확인
            if (serviceRepository.existsById(serviceDto.getServiceCode())) {
                response.put("isSuccess", false);
                response.put("message", "이미 존재하는 서비스입니다.");
                return response.toString(); // 중복된 경우
            }

            // 서비스 저장
            Service service = new Service(serviceDto.getServiceCode(), serviceDto.getServiceName(), serviceDto.getServicePrice());
            serviceRepository.save(service);

            response.put("isSuccess", true);
            response.put("message", "서비스 등록 성공!");

            return response.toString(); // 성공적으로 저장된 경우
        } catch (Exception e) {
            // 예외 발생 시 상세 오류 메시지 반환
            response.put("isSuccess", false);
            response.put("message", "입력 자료 오류입니다. " + e.getMessage());
            return response.toString(); // 오류 메시지 반환
        }
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