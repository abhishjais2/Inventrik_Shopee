package com.inventrik.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * Generic webhook request model for all Shopee push notifications.
 * <p>
 * Shopee sends a push notification with a numeric {@code code} identifying the
 * event type and a {@code data} map containing the event-specific payload.
 * <p>
 * The {@code code} field accepts both integer (e.g. {@code 3}) and string
 * (e.g. {@code "order_status_push"}) values for maximum compatibility.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeWebhookRequest {

    /** Shopee push mechanism code (e.g. 3 = order status) */
    private Integer code;

    /** Event type string identifier */
    @JsonProperty("event_type")
    private String eventType;

    /** Unix timestamp of the event */
    @JsonProperty("timestamp")
    private Long timestamp;

    /** Shop ID the event belongs to */
    @JsonProperty("shop_id")
    private Long shopId;

    /** Event-specific payload */
    @JsonProperty("data")
    private JsonNode data;

    public ShopeeWebhookRequest() {
    }

    public ShopeeWebhookRequest(Integer code, String eventType, Long timestamp, Long shopId, JsonNode data) {
        this.code = code;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.shopId = shopId;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Custom setter that handles both integer and string values for the
     * {@code code} field.
     */
    @JsonSetter("code")
    public void setCode(Object rawCode) {
        if (rawCode == null) {
            this.code = null;
        } else if (rawCode instanceof Number) {
            this.code = ((Number) rawCode).intValue();
        } else if (rawCode instanceof String) {
            String codeStr = (String) rawCode;
            try {
                this.code = Integer.parseInt(codeStr);
            } catch (NumberFormatException e) {
                this.code = mapStringToCode(codeStr);
            }
        }
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    private static final Map<String, Integer> EVENT_NAME_TO_CODE = Map.of(
            "order_status_push", 3,
            "order_tracking_push", 4,
            "banned_item_push", 6,
            "reserved_stock_change_push", 8,
            "customer_join", 10,
            "violation_item_push", 16,
            "shopee_auto_correct_item_push", 17,
            "item_price_update_push", 22,
            "item_scheduled_publish_failed_push", 27);

    private static Integer mapStringToCode(String eventName) {
        return EVENT_NAME_TO_CODE.getOrDefault(eventName.toLowerCase(), null);
    }
}
