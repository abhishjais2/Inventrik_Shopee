package com.inventrik.shopee.model.auth;

/**
 * In-memory representation of a cached Shopee access token.
 * Used by {@code TokenCacheManager} to track token validity.
 */
public class ShopeeTokenInfo {

    /** Current access token */
    private String accessToken;

    /** Current refresh token */
    private String refreshToken;

    /** Epoch second at which the access token expires */
    private long expiresAtEpochSecond;

    public ShopeeTokenInfo() {
    }

    public ShopeeTokenInfo(String accessToken, String refreshToken, long expiresAtEpochSecond) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAtEpochSecond = expiresAtEpochSecond;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresAtEpochSecond() {
        return expiresAtEpochSecond;
    }

    public void setExpiresAtEpochSecond(long expiresAtEpochSecond) {
        this.expiresAtEpochSecond = expiresAtEpochSecond;
    }

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
