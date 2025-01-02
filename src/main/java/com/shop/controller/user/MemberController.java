package com.shop.controller.user;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    // 직책별 디자이너 목록을 반환하는 API
    @GetMapping("/user/members")
    public List<Member> getDesignersByJob(@RequestParam(required = false, defaultValue = "원장") String job) {
        return memberRepository.findByMemberJob(job); // member_job으로 디자이너 필터링
    }
}