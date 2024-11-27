package com.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
}
