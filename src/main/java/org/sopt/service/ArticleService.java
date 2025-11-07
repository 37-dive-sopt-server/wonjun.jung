package org.sopt.service;

import org.sopt.domain.Tag;
import org.sopt.dto.response.ArticleResponse;

import java.util.List;

public interface ArticleService {

    ArticleResponse create(Long memberId, String title, String content, Tag tag);

    ArticleResponse findById(Long id);

    List<ArticleResponse> findAll();

    List<ArticleResponse> search(String title, String memberName);

}
