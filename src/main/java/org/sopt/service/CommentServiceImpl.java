package org.sopt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.ErrorCode;
import org.sopt.domain.Article;
import org.sopt.domain.Comment;
import org.sopt.domain.Member;
import org.sopt.dto.response.CommentResponse;
import org.sopt.exception.BusinessException;
import org.sopt.repository.ArticleRepository;
import org.sopt.repository.CommentRepository;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;


    // 댓글 생성
    @Transactional
    public Long create(Long articleId, Long memberId, String content) {
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new BusinessException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );

        Comment comment = Comment.of(article, member, content);

        commentRepository.save(comment);

        return comment.getId();
    }

    // 댓글 조회
    public CommentResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND)
        );

        return CommentResponse.from(comment);
    }
    
    // 댓글 삭제
    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND)
        );
        commentRepository.delete(comment);
    }
    
    // 댓글 수정
    @Transactional
    public CommentResponse update(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND)
        );
        comment.update(content);

        return CommentResponse.from(comment);
    }
}
