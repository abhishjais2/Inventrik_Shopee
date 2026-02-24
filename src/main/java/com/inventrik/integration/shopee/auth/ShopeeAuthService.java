package com.inventrik.integration.shopee.auth;

import com.inventrik.integration.shopee.config.ShopeeConfig;
import com.inventrik.integration.shopee.constant.ShopeeConstants;
import com.inventrik.integration.shopee.exception.ShopeeApiException;
import com.inventrik.integration.shopee.model.auth.ShopeeTokenInfo;
import com.inventrik.integration.shopee.model.auth.ShopeeTokenRequest;
import com.inventrik.integration.shopee.model.auth.ShopeeTokenResponse;
import com.inventrik.integration.shopee.util.ShopeeSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Handles the Shopee OAuth token lifecycle:
 * <ol>
 * <li>Exchange authorization code for access + refresh tokens</li>
 * <li>Refresh an expired access token using the refresh token</li>
 * </ol>
 * <p>
 * All obtained tokens are automatically stored in {@link TokenCacheManager}.
 */
@Service
public class ShopeeAuthService {

    private static final Logger log = LoggerFactory.getLogger(ShopeeAuthService.class);

    private final RestTemplate restTemplate;
    private final TokenCacheManager tokenCacheManager;

    public ShopeeAuthService(RestTemplate restTemplate, TokenCacheManager tokenCacheManager) {
        this.restTemplate = restTemplate;
        this.tokenCacheManager = tokenCacheManager;
    }

    // ── Public API ───────────────────────────────────────────────────────

    /**
     * Exchange an authorization code for access and refresh tokens.
     * Called once after the seller authorizes the app via the Shopee OAuth page.
     *
     * @param authCode the one-time authorization code from Shopee callback
     * @param shopId   the shop ID from the callback
     * @return the resulting token info (also cached automatically)
     */
    public ShopeeTokenInfo getAccessToken(String authCode, long shopId) {
        log.info("Exchanging auth code for access token [shopId={}]", shopId);

        long timestamp = System.currentTimeMillis() / 1000;
        String apiPath = ShopeeConstants.API_AUTH_TOKEN_GET;
        String sign = ShopeeSignatureUtil.generateSignAuth(
                ShopeeConfig.PARTNER_ID, apiPath, timestamp, ShopeeConfig.PARTNER_KEY);

        String url = buildAuthUrl(apiPath, timestamp, sign);

        ShopeeTokenRequest requestBody = ShopeeTokenRequest.builder()
                .code(authCode)
                .shopId(shopId)
                .partnerId(ShopeeConfig.PARTNER_ID)
                .build();

        ShopeeTokenResponse response = callTokenEndpoint(url, requestBody);
        ShopeeTokenInfo tokenInfo = toTokenInfo(response);
        tokenCacheManager.cacheToken(shopId, tokenInfo);

        log.info("Access token obtained successfully [shopId={}, expiresIn={}s]",
                shopId, response.getExpireIn());
        return tokenInfo;
    }

    /**
     * Refresh an expired access token using the stored refresh token.
     *
     * @param shopId the shop whose token should be refreshed
     * @return the new token info (also cached automatically)
     */
    public ShopeeTokenInfo refreshAccessToken(long shopId) {
        log.info("Refreshing access token [shopId={}]", shopId);

        ShopeeTokenInfo existingToken = tokenCacheManager.getCachedToken(shopId);
        if (existingToken == null || existingToken.getRefreshToken() == null) {
            throw new ShopeeApiException("No refresh token available for shopId=" + shopId
                    + ". Re-authorization required.");
        }

        long timestamp = System.currentTimeMillis() / 1000;
        String apiPath = ShopeeConstants.API_AUTH_ACCESS_TOKEN_GET;
        String sign = ShopeeSignatureUtil.generateSignAuth(
                ShopeeConfig.PARTNER_ID, apiPath, timestamp, ShopeeConfig.PARTNER_KEY);

        String url = buildAuthUrl(apiPath, timestamp, sign);

        ShopeeTokenRequest requestBody = ShopeeTokenRequest.builder()
                .refreshToken(existingToken.getRefreshToken())
                .shopId(shopId)
                .partnerId(ShopeeConfig.PARTNER_ID)
                .build();

        ShopeeTokenResponse response = callTokenEndpoint(url, requestBody);
        ShopeeTokenInfo newTokenInfo = toTokenInfo(response);
        tokenCacheManager.cacheToken(shopId, newTokenInfo);

        log.info("Access token refreshed successfully [shopId={}, expiresIn={}s]",
                shopId, response.getExpireIn());
        return newTokenInfo;
    }

    /**
     * Ensure a valid access token is available for the given shop,
     * refreshing automatically if expired.
     *
     * @param shopId the shop to get a valid token for
     * @return a valid access token string
     */
    public String ensureValidAccessToken(long shopId) {
        ShopeeTokenInfo tokenInfo = tokenCacheManager.getValidToken(shopId);
        if (tokenInfo != null) {
            return tokenInfo.getAccessToken();
        }
        // Token expired or missing — attempt refresh
        log.info("Token expired or missing for shopId={}, attempting refresh", shopId);
        ShopeeTokenInfo refreshed = refreshAccessToken(shopId);
        return refreshed.getAccessToken();
    }

    // ── Internals ────────────────────────────────────────────────────────

    private String buildAuthUrl(String apiPath, long timestamp, String sign) {
        return UriComponentsBuilder
                .fromHttpUrl(ShopeeConfig.BASE_URL + apiPath)
                .queryParam("partner_id", ShopeeConfig.PARTNER_ID)
                .queryParam("timestamp", timestamp)
                .queryParam("sign", sign)
                .build()
                .toUriString();
    }

    private ShopeeTokenResponse callTokenEndpoint(String url, ShopeeTokenRequest requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ShopeeTokenRequest> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ShopeeTokenResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
                    ShopeeTokenResponse.class);

            ShopeeTokenResponse response = responseEntity.getBody();
            if (response == null) {
                throw new ShopeeApiException("Empty response from Shopee auth endpoint");
            }
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new ShopeeApiException(
                        responseEntity.getStatusCode().value(),
                        response.getError(),
                        "Auth failed: " + response.getMessage(),
                        response.getRequestId());
            }
            return response;
        } catch (ShopeeApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to call Shopee auth endpoint: {}", e.getMessage());
            throw new ShopeeApiException("Failed to obtain token from Shopee", e);
        }
    }

    private ShopeeTokenInfo toTokenInfo(ShopeeTokenResponse response) {
        long nowEpoch = System.currentTimeMillis() / 1000;
        int expiresIn = response.getExpireIn() != null ? response.getExpireIn() : 14400;
        return ShopeeTokenInfo.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .expiresAtEpochSecond(nowEpoch + expiresIn)
                .build();
    }
}
