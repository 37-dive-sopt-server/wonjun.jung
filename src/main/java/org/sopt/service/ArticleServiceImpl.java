package org.sopt.service;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Article;
import org.sopt.domain.Member;
import org.sopt.domain.Tag;
import org.sopt.dto.response.ArticleResponse;
import org.sopt.exception.BusinessException;
import org.sopt.repository.ArticleRepository;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    // 아티클 생성
    @Transactional
    public ArticleResponse create(Long memberId, String title, String content, Tag tag) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Article article = Article.of(
                member, title, content, tag
        );

        Article savedArticle = articleRepository.save(article);
        return ArticleResponse.from(savedArticle);
    }

    // 아티클 단일 조회
    @Transactional
    public ArticleResponse findById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTICLE_NOT_FOUND));
        return ArticleResponse.from(article);
    }

    // 아티클 전체 조회
    @Transactional
    public List<ArticleResponse> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleResponse::from)
                .toList();
    }

    // 아티클 검색
    @Transactional(readOnly = true)
    public List<ArticleResponse> search(String title, String memberName) {
        return articleRepository.findByTitleContainingAndMember_NameContaining(title, memberName).stream()
                .map(ArticleResponse::from)
                .toList();
    }
}
