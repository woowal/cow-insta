package com.example.cowinstagram.member.service;

import com.example.cowinstagram.amazonS3.AmazonS3Service;
import com.example.cowinstagram.comment.service.CommentService;
import com.example.cowinstagram.follow.service.FollowService;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import com.example.cowinstagram.member.dto.response.MemberResponse;
import com.example.cowinstagram.post.service.PostService;
import com.example.cowinstagram.reply.service.ReplyService;
import com.example.cowinstagram.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final FollowService followService;
    private final ReplyService replyService;
    private final AmazonS3Service amazonS3Service;

    @Transactional(readOnly = true)
    public MemberResponse findOne(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("멤버가 존재하지 않습니다."));

        return MemberResponse.from(member);
    }

    @Transactional
    public void update(Long id, MemberUpdateRequest memberUpdateRequest, MultipartFile multipartFile) throws IOException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("멤버가 존재하지 않습니다."));
        amazonS3Service.deleteImage(member.getImageUrl());
        String imageUrl = amazonS3Service.saveFile(multipartFile);
        member.updateImageUrl(imageUrl);
        member.update(memberUpdateRequest);
    }

    @Transactional
    public void delete(Long id) throws MalformedURLException {
        Member member = memberRepository.findById(id).get();
        followService.deleteFollowsByMember(member);
        replyService.deleteAllByMember(member);
        commentService.deleteAllByMember(member);
        postService.deleteAllByMember(member);
        memberRepository.deleteById(id);
        amazonS3Service.deleteImage(member.getImageUrl());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}

