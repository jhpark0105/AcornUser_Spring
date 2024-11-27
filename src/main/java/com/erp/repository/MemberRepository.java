package com.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	Member findByMemberId(String memberId);
}
