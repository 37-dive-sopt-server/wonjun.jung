package org.sopt.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiNotFoundFallbackController {

    // /api 자체로 오는 요청
    @RequestMapping({"", "/"})
    public ResponseEntity<ApiResponse<Void>> root() {
        return ResponseEntity.status(404)
                .body(ApiResponse.error(404, "요청 URL이 올바르지 않습니다."));
    }

    // /api/** 하위의 존재하지 않는 모든 경로
    @RequestMapping("/**")
    public ResponseEntity<ApiResponse<Void>> fallback() {
        return ResponseEntity.status(404)
                .body(ApiResponse.error(404, "요청 URL이 올바르지 않습니다."));
    }
}
