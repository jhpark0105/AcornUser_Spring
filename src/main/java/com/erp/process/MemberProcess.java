package com.erp.process;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.dto.MemberDto;
import com.erp.entity.Member;
import com.erp.repository.MemberRepository;

@Repository
public class MemberProcess {
	@Autowired
	private MemberRepository repository;
	
	public List<MemberDto> getAllList() {
		return repository.findAll()
				.stream()
				.map(MemberDto :: fromEntity)
				.collect(Collectors.toList());
	}
	
	public String insert(MemberDto dto) {
		// 입력한 번호 중복 확인 
		try {
			repository.findById(dto.getMemberId()).get();
			return "이미 등록된 번호입니다.";
		} catch(Exception e) {
			//  findById의 결과가 error인 경우 (등록가능)
			try {
				Member newData = MemberDto.toEntity(dto);
				repository.save(newData);
				return "Success";
			} catch (Exception e2) {
				return "입력 자료 오류입니다." + e2;
			}
		}
	}
	
	// 수정 삭제를 위한 데이터 1개 읽기
	public Member getData(String memberId) {
		Member member = repository.findByMemberId(memberId);
		return member;
	}
	
	// 수정
	public String update(MemberDto dto) {
		try {
			Member member = MemberDto.toEntity(dto);
			repository.save(member);
			return "Success";
		}
		catch(Exception e) {
			return "수정 자료 오류입니다 " +e.getMessage();
		}
	}
	
	// 삭제
	public String delete(String memberId) {
		try {
			repository.deleteById(memberId);
			return "Success";
		} catch (Exception e) {
			return "삭제 작업 오류입니다" + e.getMessage();
		}
	}
}






