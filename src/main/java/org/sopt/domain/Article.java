package org.sopt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(unique = true)
    private String title;

    private String content;

    private Tag tag;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="article", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

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
}
