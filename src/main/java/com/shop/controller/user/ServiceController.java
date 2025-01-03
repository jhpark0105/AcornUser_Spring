package com.shop.controller.user;

import com.shop.entity.Service;
import com.shop.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;

    // 모든 서비스 목록 반환 API
    @GetMapping("/user/service")
    public List<Service> getAllServices() {
        return serviceRepository.findAll(); // 모든 서비스 반환
    }
}