package com.shop.entity;

import java.time.LocalDate;

import com.shop.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="member_name")
	private String memberName;

	@Column(name = "member_password")
	private String memberPassword;
	
	@Column(name="member_job")
	private String memberJob;
	
	@Column(name="member_date")
	private LocalDate memberDate;
	
	@Column(name="member_tel")
	private String memberTel;
	
	@Column(name="member_cnt")
	private int memberCnt;
	
	@Column(name="branch_code")
	private String branchCode;
	
	public static Member fromDto(MemberDto dto) {
		return Member.builder()
				.memberId(dto.getMemberId())
				.memberName(dto.getMemberName())
				.memberPassword(dto.getMemberPassword())
				.memberJob(dto.getMemberJob())
				.memberDate(dto.getMemberDate())
				.memberTel(dto.getMemberTel())
				.memberCnt(dto.getMemberCnt())
				.branchCode(dto.getBranchCode())
				.build();
	}
	
}