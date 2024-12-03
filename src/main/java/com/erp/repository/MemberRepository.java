package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	Member findByMemberId(String memberId);
	
	// 이름으로 검색
	Member findByMemberName(String memberName);
	
	// 직책으로 검색
	Member findByMemberJob(String memberJob);
}