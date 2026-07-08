package com.pg.util;

import java.util.List;

import com.pg.dto.ErrorResponse;

public class ErrorResponseUtil {

	public static ErrorResponse buildError(String message) {
		return ErrorResponse.of(message);
	}

	public static ErrorResponse buildError(String message, List<ErrorResponse.FieldErrorResponse> errors) {
		return ErrorResponse.of(message, errors);
	}
}
