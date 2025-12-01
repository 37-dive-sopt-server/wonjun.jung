package org.sopt.dto.response;

import org.sopt.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Article article,
        Writer writer,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record Article(Long id, String title) {}

    public record Writer(Long id, String name) {}

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                new Article(
                        comment.getArticle().getId(),
                        comment.getArticle().getTitle()
                ),
                new Writer(
                        comment.getMember().getId(),
                        comment.getMember().getName()
                ),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
