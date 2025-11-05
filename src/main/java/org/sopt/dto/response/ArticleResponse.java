package org.sopt.dto.response;

import org.sopt.domain.Article;
import org.sopt.domain.Tag;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        Author author,
        String title,
        String content,
        Tag tag,
        LocalDateTime createdAt
) {
    public record Author(Long memberId, String name) {}

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getId(),
                new Author(
                        article.getMember().getId(),
                        article.getMember().getName()
                ),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getCreatedAt()
        );
    }
}
