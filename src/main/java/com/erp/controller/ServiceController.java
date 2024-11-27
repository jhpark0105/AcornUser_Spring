package com.erp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.erp.dto.ServiceDto;
import com.erp.process.ServiceProcess;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 서버 주소
public class ServiceController {
    @Autowired
    private ServiceProcess serviceProcess;
    
    // List 불러오기
    @GetMapping("service")
    public List<ServiceDto> listProcess() {
    	
        return serviceProcess.getDataAll();
    }

    // insertProcess
    @PostMapping("service")
    public Map<String, Object> insertProcess(@RequestBody ServiceDto serviceDto) {
    	serviceProcess.insert(serviceDto);
        
        return Map.of("isSuccess", true);
    }
    
//    @PutMapping("/service/{serviceCode}")
//    public Map<String, Object> updateProcess(@PathVariable("serviceCode") String serviceCode, @RequestBody ServiceDto serviceDto) {
//        serviceService.update(serviceCode, serviceDto);  // 서비스 수정 로직
//        return Map.of("isSuccess", true);
//    }
    
    // updateProcess
    @PutMapping("/service/{serviceCode}")
    public Map<String, Object> updateProcess(@PathVariable("serviceCode") String serviceCode, @RequestBody ServiceDto serviceDto) {
        serviceDto.setServiceCode(serviceCode); 
        String result = serviceProcess.update(serviceDto);

        return Map.of("isSuccess", true);
    }
    
    // deleteProcess
    @DeleteMapping("/service/{serviceCode}")
    public Map<String, Object> deleteProcess(@PathVariable("serviceCode") String serviceCode) {
    	serviceProcess.delete(serviceCode);
        
        
        return Map.of("isSuccess", true);
    }
}