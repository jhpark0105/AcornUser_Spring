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
	public static BranchDto fromEntity(Branch entity) {
		return BranchDto.builder()
				.branchCode(entity.getBranchCode())
				.branchPw(entity.getBranchPw())
				.branchName(entity.getBranchName())
				.branchTel(entity.getBranchTel())
				.branchAddress(entity.getBranchAddress())
				.branchNote(entity.getBranchNote())
				.build();
	}
	
}
