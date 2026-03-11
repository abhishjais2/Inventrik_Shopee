package com.inventrik.shopee.constant;

/**
 * Central constants for Shopee webhook event types and API paths.
 * <p>
 * Adding a new event type? Just add a constant here and a handler method
 * in {@code ShopeeWebhookProcessor} — no controller changes needed.
 */
public final class ShopeeConstants {

    private ShopeeConstants() {
        // Utility class — no instantiation
    }

    // ── Webhook Push Mechanism Codes ─────────────────────────────────────
    /** Shopee push code 3 — order status change */
    public static final int PUSH_CODE_ORDER_STATUS = 3;
    /** Shopee push code — payment update (placeholder, varies by region) */
    public static final int PUSH_CODE_PAYMENT_UPDATE = 4;
    /** Shopee push code — customer join */
    public static final int PUSH_CODE_CUSTOMER_JOIN = 10;

    // ── Webhook Event Type Strings ───────────────────────────────────────
    public static final String EVENT_ORDER_STATUS = "ORDER_STATUS";
    public static final String EVENT_PAYMENT_UPDATE = "PAYMENT_UPDATE";
    public static final String EVENT_CUSTOMER_JOIN = "CUSTOMER_JOIN";

    // ── Shopee API v2 Paths ──────────────────────────────────────────────
    public static final String API_AUTH_TOKEN_GET = "/api/v2/auth/token/get";
    public static final String API_AUTH_ACCESS_TOKEN_GET = "/api/v2/auth/access_token/get";

    public static final String API_ORDER_GET_DETAIL = "/api/v2/order/get_order_detail";
    public static final String API_ORDER_GET_LIST = "/api/v2/order/get_order_list";
    public static final String API_ORDER_CANCEL = "/api/v2/order/cancel_order";

    public static final String API_LOGISTICS_SHIP_ORDER = "/api/v2/logistics/ship_order";
    public static final String API_LOGISTICS_GET_TRACKING = "/api/v2/logistics/get_tracking_number";

    // ── HTTP ─────────────────────────────────────────────────────────────
    public static final String CONTENT_TYPE_JSON = "application/json";
}
