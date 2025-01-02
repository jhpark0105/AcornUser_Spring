package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String>{
	Member findByMemberId(String memberId);
	
	// 이름으로 검색
	Member findByMemberName(String memberName);

//	// 직책으로 검색
//	Member findByMemberJob(String memberJob);

	// member_job을 기준으로 직책별 디자이너를 조회
	List<Member> findByMemberJob(String memberJob);
}