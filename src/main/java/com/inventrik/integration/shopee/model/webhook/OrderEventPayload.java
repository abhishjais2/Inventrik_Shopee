package com.inventrik.integration.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload model for order-related webhook events (push code 3 —
 * order_status_push).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEventPayload {

    /** Shopee order serial number */
    @JsonProperty("ordersn")
    private String orderSn;

    /** Updated order status (e.g. UNPAID, READY_TO_SHIP, COMPLETED, CANCELLED) */
    @JsonProperty("status")
    private String status;

    /** Unix timestamp of the status update */
    @JsonProperty("update_time")
    private Long updateTime;
}
