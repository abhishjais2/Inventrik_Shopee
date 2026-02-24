package com.inventrik.integration.shopee.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler that returns structured JSON error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle Shopee-specific API exceptions.
     */
    @ExceptionHandler(ShopeeApiException.class)
    public ResponseEntity<Map<String, Object>> handleShopeeApiException(ShopeeApiException ex) {
        log.error("Shopee API error [httpStatus={}, errorCode={}, requestId={}]: {}",
                ex.getHttpStatus(), ex.getErrorCode(), ex.getRequestId(), ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("error", ex.getErrorCode() != null ? ex.getErrorCode() : "shopee_api_error");
        body.put("message", ex.getMessage());
        body.put("httpStatus", ex.getHttpStatus());
        body.put("requestId", ex.getRequestId());

        HttpStatus status = ex.getHttpStatus() > 0
                ? HttpStatus.valueOf(ex.getHttpStatus())
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Catch-all for unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("error", "internal_error");
        body.put("message", "An unexpected error occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
