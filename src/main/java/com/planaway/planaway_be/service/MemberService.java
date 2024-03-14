package com.planaway.planaway_be.service;

import com.planaway.planaway_be.dto.MemberDTO;
import com.planaway.planaway_be.entity.MemberEntity;
import com.planaway.planaway_be.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        //repository 의 save 메서드 호출 (조건-> entity 객체를 넘겨줘야 함)
        //1. dto -> entity 객체로 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);

    }

    public MemberDTO login(MemberDTO memberDTO) {
        //1. 회원이 입력한 이메일로 DB에서 조회하기
        //2. DB에서 조회한 비밀번호, 사용자가 입력한 비밀번호가 일치했는지 판단하기

    }
}
