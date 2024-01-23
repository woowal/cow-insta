package com.example.cowinstagram.member.service;

import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import com.example.cowinstagram.member.dto.response.MemberResponse;
import com.example.cowinstagram.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse findOne(String id) {
        Member member = memberRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchElementException("멤버가 존재하지 않습니다."));

        return MemberResponse.from(member);
    }

    @Transactional
    public void update(Long id, MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("멤버가 존재하지 않습니다."));
        member.update(memberUpdateRequest);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}

