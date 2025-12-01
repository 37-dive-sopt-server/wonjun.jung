package org.sopt.dto.response;

import org.sopt.domain.Article;
import org.sopt.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        Long id,
        Author author,
        String title,
        String content,
        Tag tag,
        LocalDateTime createdAt,
        List<Comment> comment
) {
    public record Author(Long memberId, String name) {}

    public record Comment(Long commentId, Long memberId, String comment) {}

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
                article.getCreatedAt(),
                article.getComments().stream()
                        .map(comment -> new Comment(
                                comment.getId(),
                                comment.getMember().getId(),
                                comment.getContent()
                        ))
                        .toList()
        );
    }
}
