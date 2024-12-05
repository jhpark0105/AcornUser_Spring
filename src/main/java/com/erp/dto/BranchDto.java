package com.erp.dto;

import com.erp.entity.Branch;

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
public class BranchDto {
	private String branchCode;

    private String branchPw;

    private String branchName;

    private String branchTel;

    private String branchAddress;

    private String branchNote;
    
 // Entity -> DTO 변환 메서드
    public static BranchDto fromEntity(Branch branch) {
        return BranchDto.builder()
                .branchCode(branch.getBranchCode())
                .branchPw(branch.getBranchPw())
                .branchName(branch.getBranchName())
                .branchTel(branch.getBranchTel())
                .branchAddress(branch.getBranchAddress())
                .branchNote(branch.getBranchNote())
                .build();
    }
    
    // DTO -> Entity 변환 메서드
    public Branch toEntity() {
    	return Branch.builder()
    			.branchCode(this.branchCode)
    			.branchPw(this.branchPw)
    			.branchName(this.branchName)
    			.branchTel(this.branchTel)
    			.branchAddress(this.branchAddress)
    			.branchNote(this.branchNote)
    			.build();
    }
}
