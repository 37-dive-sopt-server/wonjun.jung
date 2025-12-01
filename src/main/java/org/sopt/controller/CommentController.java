package org.sopt.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.common.ApiResponse;
import org.sopt.dto.request.CommentCreateRequest;
import org.sopt.dto.request.CommentUpdateRequest;
import org.sopt.dto.response.CommentResponse;
import org.sopt.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;
    
    // 댓글 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createComment(@RequestBody CommentCreateRequest req) {
        Long commentId = commentService.create(
                req.articleId(), req.memberId(), req.content()
        );

        return ResponseEntity.ok(ApiResponse.success(commentId));
    }
    
    // 댓글 조회
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getComment(@PathVariable("id") Long id) {
        CommentResponse res = commentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    // 댓글 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 댓글 수정
    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable("id") Long id, @RequestBody CommentUpdateRequest req) {
        CommentResponse res = commentService.update(id, req.content());
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
