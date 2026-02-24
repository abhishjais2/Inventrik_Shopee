package com.inventrik.integration.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeApiResponse<T> {

    /** Error code — empty string or null means success */
    @JsonProperty("error")
    private String error;

    /** Human-readable error/success message */
    @JsonProperty("message")
    private String message;

    /** The actual response payload */
    @JsonProperty("response")
    private T response;

    /** Shopee request ID for debugging / support tickets */
    @JsonProperty("request_id")
    private String requestId;

    /** Convenience check for success */
    public boolean isSuccess() {
        return error == null || error.isEmpty();
    }
}
