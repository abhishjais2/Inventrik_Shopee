package com.inventrik.shopee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status model returned by install/uninstall endpoints.
 * Represents the current connection state of a Shopee shop.
 */
public class ShopConnectionStatus {

    public static final String STATUS_CONNECTED = "CONNECTED";
    public static final String STATUS_DISCONNECTED = "DISCONNECTED";

    @JsonProperty("shop_id")
    private long shopId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private long timestamp;

    public ShopConnectionStatus() {
    }

    public ShopConnectionStatus(long shopId, String status, String message) {
        this.shopId = shopId;
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
