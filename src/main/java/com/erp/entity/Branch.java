package com.erp.entity;

import com.erp.dto.BranchDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
	@Id
	private String branchCode;
	private String branchPw;
	private String branchName;
	private String branchTel;
	private String branchAddress;
	private String branchNote;
	
	// toEntity
	public static Branch toEntity(BranchDto dto) {
		return Branch.builder()
				.branchCode(dto.getBranchCode())
				.branchPw(dto.getBranchPw())
				.branchName(dto.getBranchName())
				.branchTel(dto.getBranchTel())
				.branchAddress(dto.getBranchAddress())
				.branchNote(dto.getBranchNote())
				.build();
	}
}
