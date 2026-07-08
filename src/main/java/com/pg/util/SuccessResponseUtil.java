package com.pg.util;

import com.pg.dto.ApiResponse;

public final class SuccessResponseUtil {

    private SuccessResponseUtil() {
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.of(message, data);
    }

    public static ApiResponse<Void> success(String message) {
        return ApiResponse.of(message);
    }
}
