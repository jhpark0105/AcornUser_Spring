package com.erp.dto;

import java.time.LocalDate;

import com.erp.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
	private String memberId, memberName, memberJob, memberTel, branchCode;
	private LocalDate memberDate;
	private int memberCnt;

	public static MemberDto fromEntity(Member member) {
		return MemberDto.builder()
				.memberId(member.getMemberId())
				.memberName(member.getMemberName())
				.memberJob(member.getMemberJob())
				.memberDate(member.getMemberDate())
				.memberTel(member.getMemberTel())
				.memberCnt(member.getMemberCnt())
				.branchCode(member.getBranchCode())
				.build();
	}
}
