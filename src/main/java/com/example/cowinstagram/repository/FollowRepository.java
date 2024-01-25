package com.example.cowinstagram.repository;

import com.example.cowinstagram.follow.domain.Follow;
import com.example.cowinstagram.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFollower(Member member);

    List<Follow> findAllByFollowing(Member member);

    void deleteByFollowerAndFollowing(Member follower, Member following);

    void deleteAllByFollower(Member follower);

    void deleteAllByFollowing(Member following);
}
