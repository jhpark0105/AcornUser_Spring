package com.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BranchSignUpRequestDto {
	
	private String branchCode;
	private String branchPw;
	private String branchName  = "ROLE_BRANCH";
	private String branchTel;
	private String branchAddress;
	private String branchNote;
	private String branchRole;
}
