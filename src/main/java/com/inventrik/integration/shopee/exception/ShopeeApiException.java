package com.inventrik.integration.shopee.exception;

import lombok.Getter;

/**
 * Custom exception for Shopee API communication failures.
 * <p>
 * Carries HTTP status, Shopee error code, and a human-readable message
 * so callers can make informed retry/fallback decisions.
 */
@Getter
public class ShopeeApiException extends RuntimeException {

    /** HTTP status code returned by Shopee (e.g. 401, 500) — 0 if not applicable */
    private final int httpStatus;

    /** Shopee error code string (e.g. "error_auth") */
    private final String errorCode;

    /** Shopee request ID for support debugging */
    private final String requestId;

    public ShopeeApiException(String message) {
        super(message);
        this.httpStatus = 0;
        this.errorCode = null;
        this.requestId = null;
    }

    public ShopeeApiException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = 0;
        this.errorCode = null;
        this.requestId = null;
    }

    public ShopeeApiException(int httpStatus, String errorCode, String message, String requestId) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.requestId = requestId;
    }

    public ShopeeApiException(int httpStatus, String errorCode, String message, String requestId, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.requestId = requestId;
    }
}
