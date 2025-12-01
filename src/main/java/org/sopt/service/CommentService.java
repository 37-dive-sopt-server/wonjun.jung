package org.sopt.service;

import org.sopt.dto.response.CommentResponse;

public interface CommentService {

    Long create(Long articleId, Long memberId, String content);

    CommentResponse findById(Long id);

    void delete(Long commentId);

    void update(Long commentId, String content);
}
