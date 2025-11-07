package org.sopt.repository;

import org.sopt.domain.Article;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> searchWithDynamicQuery(String title, String memberName);
}
