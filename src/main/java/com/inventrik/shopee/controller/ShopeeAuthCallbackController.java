package com.inventrik.shopee.controller;

import com.inventrik.shopee.auth.ShopeeAuthService;
import com.inventrik.shopee.model.auth.ShopeeTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OAuth callback endpoint for Shopee Shop Authorization.
 * <p>
 * After the seller authorizes your app on the Shopee auth page, Shopee
 * redirects the browser to your {@code redirect_url} with query parameters:
 * <ul>
 * <li>{@code code} — the one-time authorization code</li>
 * <li>{@code shop_id} — the shop that was authorized</li>
 * </ul>
 * <p>
 * This controller captures those parameters and exchanges the code for tokens.
 */
@RestController
public class ShopeeAuthCallbackController {

    private static final Logger log = LoggerFactory.getLogger(ShopeeAuthCallbackController.class);

    private final ShopeeAuthService authService;

    public ShopeeAuthCallbackController(ShopeeAuthService authService) {
        this.authService = authService;
    }

    /**
     * Shopee OAuth callback handler.
     * <p>
     * Shopee redirects here after the seller clicks "Confirm Authorization":
     * {@code GET /api/shopee/auth/callback?code=...&shop_id=...}
     * <p>
     * This endpoint exchanges the authorization code for access + refresh tokens.
     */
    @GetMapping("/api/shopee/auth/callback")
    public ResponseEntity<Map<String, Object>> handleAuthCallback(
            @RequestParam("code") String code,
            @RequestParam("shop_id") long shopId) {

        log.info("Received Shopee auth callback [shopId={}, code={}...]",
                shopId, code.substring(0, Math.min(code.length(), 8)));

        try {
            ShopeeTokenInfo tokenInfo = authService.getAccessToken(code, shopId);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Shop authorized successfully!");
            response.put("shop_id", shopId);
            response.put("access_token", tokenInfo.getAccessToken());
            response.put("refresh_token", tokenInfo.getRefreshToken());
            response.put("expires_at_epoch", tokenInfo.getExpiresAtEpochSecond());

            log.info("Shop authorization completed successfully [shopId={}]", shopId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Failed to exchange auth code for tokens [shopId={}]: {}", shopId, e.getMessage());

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Failed to exchange authorization code: " + e.getMessage());
            errorResponse.put("shop_id", shopId);

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
