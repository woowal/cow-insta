package com.example.cowinstagram.follow.domain;

import com.example.cowinstagram.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following")
    private Member following;

    @Builder
    public Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }

}
