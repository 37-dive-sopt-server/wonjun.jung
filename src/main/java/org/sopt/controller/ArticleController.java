package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.common.ApiResponse;
import org.sopt.dto.request.ArticleCreateRequest;
import org.sopt.dto.response.ArticleResponse;
import org.sopt.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 아티클 생성
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponse>> create(@Valid @RequestBody ArticleCreateRequest req) {
        ArticleResponse res = articleService.create(req.memberId(), req.title(), req.content(), req.tag());
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    // 아티클 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> findOne(@PathVariable Long id) {
        ArticleResponse res = articleService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    // 아티클 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> findAll() {
        List<ArticleResponse> res = articleService.findAll();
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
