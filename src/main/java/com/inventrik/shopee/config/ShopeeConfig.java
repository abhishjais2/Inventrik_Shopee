package com.inventrik.shopee.config;

/**
 * Central configuration for Shopee API credentials and endpoints.
 * <p>
 * Currently uses static fields for simplicity.
 * Designed for easy migration to {@code @Value} / environment variables /
 * secret vault later.
 * <p>
 * IMPORTANT: Never log the {@code partnerKey} — it is your HMAC signing secret.
 */
public class ShopeeConfig {

    // ── Credentials ──────────────────────────────────────────────────────
    /** Shopee Partner ID (obtained from Open Platform Console) */
    public static final long PARTNER_ID = 1212400L; // TODO: replace with real partner ID

    /** Shopee Partner Key (HMAC-SHA256 signing secret) */
    public static final String PARTNER_KEY = "shpk77475757435757577646786b49706c504c694a4a764f505a774352475078"; // REPLACE_WITH_PARTNER_KEY

    /** Default Shop ID for single-shop integrations */
    public static final long SHOP_ID = 226455707L; // TODO: replace with real shop ID

    // ── OAuth Redirect ──────────────────────────────────────────────────
    /**
     * Redirect URL for OAuth callback.
     * Set this to your Cloudflare Tunnel URL + callback path.
     * Example: https://your-tunnel.trycloudflare.com/api/shopee/auth/callback
     */
    public static final String REDIRECT_URL = "https://hardwood-friday-airport-offered.trycloudflare.com/api/shopee/auth/callback";

    // ── Base URLs ────────────────────────────────────────────────────────
    /** Sandbox / test environment (official Shopee sandbox for test credentials) */
    public static final String BASE_URL_SANDBOX = "https://openplatform.sandbox.test-stable.shopee.sg";

    /** Production environment */
    public static final String BASE_URL_PRODUCTION = "https://partner.shopeemobile.com";

    /** Active base URL — switch between sandbox and production here */
    public static final String BASE_URL = BASE_URL_SANDBOX;

    // ── Timeouts ─────────────────────────────────────────────────────────
    /** HTTP connection timeout in milliseconds */
    public static final int CONNECT_TIMEOUT_MS = 5_000;

    /** HTTP read timeout in milliseconds */
    public static final int READ_TIMEOUT_MS = 30_000;

    // ── Token Settings ───────────────────────────────────────────────────
    /** Buffer (in seconds) before actual expiry to trigger token refresh */
    public static final int TOKEN_EXPIRY_BUFFER_SECONDS = 300; // 5 minutes

    private ShopeeConfig() {
        // Utility class — no instantiation
    }
}
