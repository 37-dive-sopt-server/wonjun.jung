package org.sopt.repository;

import org.sopt.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    // 아티클 전체 조회 (N+1 방지)
    @Query("SELECT a FROM Article a JOIN FETCH a.member")
    List<Article> findAllWithMember();

    // 아티클 단일 조회 (N+1 방지)
    @Query("SELECT a FROM Article a JOIN FETCH a.member WHERE a.id = :id")
    Optional<Article> findByIdWithMember(@Param("id") Long id);
}
