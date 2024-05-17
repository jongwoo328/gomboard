package me.jongwoo.gomboard.common.response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().message("success").data(data).build();
    }
}
