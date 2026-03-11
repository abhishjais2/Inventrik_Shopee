package com.inventrik.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic wrapper for all Shopee API responses.
 * <p>
 * Shopee v2 APIs return a consistent envelope:
 *
 * <pre>
 * {
 *   "error": "",
 *   "message": "",
 *   "response": { ... },
 *   "request_id": "..."
 * }
 * </pre>
 *
 * @param <T> type of the {@code response} payload
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeApiResponse<T> {

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private T response;

    @JsonProperty("request_id")
    private String requestId;

    public ShopeeApiResponse() {
    }

    public ShopeeApiResponse(String error, String message, T response, String requestId) {
        this.error = error;
        this.message = message;
        this.response = response;
        this.requestId = requestId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /** Convenience check for success */
    public boolean isSuccess() {
        return error == null || error.isEmpty();
    }
}
