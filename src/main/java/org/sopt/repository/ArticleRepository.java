package org.sopt.repository;

import org.sopt.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 아티클 검색
    List<Article> findByTitleContainingAndMember_NameContaining(String title, String memberName);
}
