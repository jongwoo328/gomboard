package me.jongwoo.gomboard.common.response;

import lombok.RequiredArgsConstructor;

public record CommonResponse<T>(
        String message,
        T data
) {
}
