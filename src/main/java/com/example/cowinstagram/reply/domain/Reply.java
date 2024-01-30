package com.example.cowinstagram.reply.domain;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "comment")
    private Comment comment;

    private String content;

    @Builder
    public Reply(Member member, Comment comment, String content) {
        this.member = member;
        this.comment = comment;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
