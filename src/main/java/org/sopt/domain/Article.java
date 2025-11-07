package org.sopt.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_article_title", columnList = "title"),
        @Index(name = "idx_article_member_id", columnList = "memberId")
})
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String title;

    private String content;

    private Tag tag;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Article() {}

    private Article(Member member, String title, String content, Tag tag) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createdAt = LocalDateTime.now();
    }

    public static Article of(Member member, String title, String content, Tag tag) {
        return new Article(member, title, content, tag);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
