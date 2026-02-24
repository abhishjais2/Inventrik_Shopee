package com.inventrik.integration.shopee.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response from Shopee token endpoints.
 * Maps the JSON response from {@code /api/v2/auth/token/get}
 * and {@code /api/v2/auth/access_token/get}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeTokenResponse {

    /** Error code — empty string means success */
    @JsonProperty("error")
    private String error;

    /** Human-readable error message */
    @JsonProperty("message")
    private String message;

    /** Access token for API calls (valid ~4 hours) */
    @JsonProperty("access_token")
    private String accessToken;

    /** Refresh token for renewing access (valid ~30 days) */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /** Token validity in seconds (typically 14400 = 4 hours) */
    @JsonProperty("expire_in")
    private Integer expireIn;

    /** Request ID for debugging */
    @JsonProperty("request_id")
    private String requestId;
}
