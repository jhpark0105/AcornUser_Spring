package com.shop.dto;

import com.shop.entity.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private String serviceCode;  // 서비스 코드
    private String serviceName;  // 서비스 이름
    private int servicePrice;    // 서비스 가격

    // Entity -> DTO 변환 메서드
    public static ServiceDto fromEntity(Service service) {
        return ServiceDto.builder()
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .servicePrice(service.getServicePrice())
                .build();
    }
    
    // DTO -> Entity 변환 메서드
    public Service toEntity() {
        return Service.builder()
                .serviceCode(this.serviceCode)
                .serviceName(this.serviceName)
                .servicePrice(this.servicePrice)
                .build();
    }
}
