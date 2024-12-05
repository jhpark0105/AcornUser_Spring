package com.erp.entity;

import com.erp.dto.BranchDto;

import jakarta.persistence.Column;
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
    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_pw")
    private String branchPw;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_tel")
    private String branchTel;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "branch_note")
    private String branchNote;
    
//  @ManyToOne
//  @JoinColumn(name = "admin_id")
//  private Admin admin;
	
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
