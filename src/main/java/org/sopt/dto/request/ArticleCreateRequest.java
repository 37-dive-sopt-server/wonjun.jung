package org.sopt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.domain.Tag;

public record ArticleCreateRequest(
        @NotNull(message = "작성자 번호를 입력해주세요")
        Long memberId,

        @NotBlank(message = "제목을 입력해주세요")
        String title,

        @NotBlank(message = "내용을 입력해주세요")
        String content,

        @NotNull(message = "태그를 입력해주세요")
        Tag tag
) {
}
