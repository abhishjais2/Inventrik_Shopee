package com.inventrik.shopee.auth;

import com.inventrik.shopee.config.ShopeeConfig;
import com.inventrik.shopee.model.auth.ShopeeTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory cache for Shopee access tokens.
 * <p>
 * Tokens are keyed by {@code shopId} to support multi-shop scenarios.
 * Before every API call the caller should invoke {@link #getValidToken(long)}
 * which returns a cached token if still valid, or {@code null} if a refresh is
 * needed.
 */
@Component
public class TokenCacheManager {

    private static final Logger log = LoggerFactory.getLogger(TokenCacheManager.class);

    private final ConcurrentHashMap<Long, ShopeeTokenInfo> tokenCache = new ConcurrentHashMap<>();

    public void cacheToken(long shopId, ShopeeTokenInfo tokenInfo) {
        tokenCache.put(shopId, tokenInfo);
        log.info("Token cached for shopId={}, expiresAt={}", shopId, tokenInfo.getExpiresAtEpochSecond());
    }

    public ShopeeTokenInfo getValidToken(long shopId) {
        ShopeeTokenInfo tokenInfo = tokenCache.get(shopId);
        if (tokenInfo == null) {
            log.debug("No cached token for shopId={}", shopId);
            return null;
        }
        if (tokenInfo.isExpired(ShopeeConfig.TOKEN_EXPIRY_BUFFER_SECONDS)) {
            log.info("Cached token for shopId={} has expired (or within buffer window)", shopId);
            return null;
        }
        log.debug("Returning valid cached token for shopId={}", shopId);
        return tokenInfo;
    }

    public ShopeeTokenInfo getCachedToken(long shopId) {
        return tokenCache.get(shopId);
    }

    public void clearToken(long shopId) {
        tokenCache.remove(shopId);
        log.info("Cleared cached token for shopId={}", shopId);
    }
}
