package org.sopt.common;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
    // 성공
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    // 실패
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(
                errorCode.status().value(),
                errorCode.message(),
                null
        );
    }
}