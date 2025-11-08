package org.sopt.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.sopt.domain.QArticle.article;
import static org.sopt.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> searchWithDynamicQuery(String title, String memberName) {
        return queryFactory
                .selectFrom(article)
                .join(article.member, member).fetchJoin()
                .where(
                        titleContains(title),
                        memberNameContains(memberName)
                )
                .fetch();
    }

    private BooleanExpression titleContains(String title) {
        return (title != null && !title.isEmpty()) ? article.title.contains(title) : null;
    }

    private BooleanExpression memberNameContains(String memberName) {
        return (memberName != null && !memberName.isEmpty()) ? article.member.name.contains(memberName) : null;
    }
}
