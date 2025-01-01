package com.shop.dto;

import com.shop.entity.Manager;

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
public class ManagerDto {
    private String branchCode;
    private String managerName;
    private String managerTel;
    private String managerMail;
    private String branchName;
    
    // Entity -> DTO 변환 메서드
    public static ManagerDto fromEntity(Manager manager) {
        return ManagerDto.builder()
                .branchCode(manager.getBranch().getBranchCode())
                .branchName(manager.getBranch().getBranchName())
                .managerName(manager.getManagerName())
                .managerTel(manager.getManagerTel())
                .managerMail(manager.getManagerMail())
                .build();
    }
    
    // DTO -> Entity 변환 메서드
    public Manager toEntity(Manager manager) { 
        manager.setManagerName(this.managerName);
        manager.setManagerTel(this.managerTel);
        manager.setManagerMail(this.managerMail);

        return manager;
    }
}
