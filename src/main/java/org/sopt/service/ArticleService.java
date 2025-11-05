package org.sopt.service;

import org.sopt.domain.Tag;
import org.sopt.dto.response.ArticleResponse;

public interface ArticleService {

    ArticleResponse create(Long memberId, String title, String content, Tag tag);

    ArticleResponse findById(Long id);

}
