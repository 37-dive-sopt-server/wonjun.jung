package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
        @NotNull(message = "아티클 ID를 입력해주세요")
        Long articleId,

        @NotNull(message = "작성자 ID를 입력해주세요")
        Long memberId,

        @NotBlank(message = "내용을 입력해주세요")
        String content
) {
}
