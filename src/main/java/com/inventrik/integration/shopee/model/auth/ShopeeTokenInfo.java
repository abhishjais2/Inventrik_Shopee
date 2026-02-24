package com.inventrik.integration.shopee.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * In-memory representation of a cached Shopee access token.
 * Used by {@code TokenCacheManager} to track token validity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopeeTokenInfo {

    /** Current access token */
    private String accessToken;

    /** Current refresh token */
    private String refreshToken;

    /** Epoch second at which the access token expires */
    private long expiresAtEpochSecond;

    /**
     * Check whether this token has expired (or will expire within the buffer
     * window).
     *
     * @param bufferSeconds seconds before actual expiry to consider the token
     *                      expired
     * @return {@code true} if the token should be refreshed
     */
    public boolean isExpired(int bufferSeconds) {
        return System.currentTimeMillis() / 1000 >= (expiresAtEpochSecond - bufferSeconds);
    }
}
