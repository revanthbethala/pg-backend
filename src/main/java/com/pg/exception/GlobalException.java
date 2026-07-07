package com.pg.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pg.util.ErrorResponseUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseUtil.buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDataAlreadyExists(DataAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseUtil.buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler({ InvalidCredentialException.class, BadCredentialsException.class })
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, Object> error = ErrorResponseUtil.buildError(
                HttpStatus.BAD_REQUEST,
                "Validation failed");

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError ->
                        errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage()));

        error.put("errors", errors);

        return ResponseEntity.badRequest().body(error);
    }

    // Invalid JSON / Missing fields / Wrong datatype
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST,
                        "Invalid request body."));
    }

    // Wrong JSON syntax
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParse(JsonParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST,
                        "Malformed JSON."));
    }

    // Invalid datatype
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST,
                        "Invalid value type in request."));
    }

    // HTTP Method not supported
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponseUtil.buildError(HttpStatus.METHOD_NOT_ALLOWED,
                        "HTTP method not supported."));
    }

    // Database exceptions
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Database error occurred."));
    }

    // JWT Expired
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED,
                        "JWT token has expired."));
    }

    // Invalid JWT
    @ExceptionHandler({
            MalformedJwtException.class,
            UnsupportedJwtException.class,
            JwtException.class
    })
    public ResponseEntity<Map<String, Object>> handleJwtException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED,
                        "Invalid JWT token."));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AuthorizationDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseUtil.buildError(HttpStatus.FORBIDDEN,
                        "Access denied."));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()));
    }
}