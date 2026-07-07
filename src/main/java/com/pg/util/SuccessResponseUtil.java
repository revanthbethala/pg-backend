package com.pg.util;

import java.util.Map;

public final class SuccessResponseUtil {

    private SuccessResponseUtil() {
    }

    public static <T> Map<String, Object> success(String message, T data) {
        return Map.of(
                "message", message,
                "data", data
        );
    }

    public static Map<String, Object> success(String message) {
        return Map.of(
                "message", message
        );
    }
}