package com.inventrik.integration.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * Generic webhook request model for all Shopee push notifications.
 * <p>
 * Shopee sends a push notification with a numeric {@code code} identifying the
 * event type
 * and a {@code data} map containing the event-specific payload.
 * This model is intentionally flexible — {@code data} is a generic map
 * so it can accommodate any event type without modification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeWebhookRequest {

    /** Shopee push mechanism code (e.g. 3 = order status) */
    @JsonProperty("code")
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

    /** Event-specific payload — kept as a generic map for flexibility */
    @JsonProperty("data")
    private JsonNode data;
}
