package com.inventrik.integration.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for cancelling an order on Shopee.
 * Maps to {@code POST /api/v2/order/cancel_order}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderRequest {

    /** Shopee order serial number */
    @JsonProperty("order_sn")
    private String orderSn;

    /** Cancellation reason code */
    @JsonProperty("cancel_reason")
    private String cancelReason;

    /** Item ID (required for partial cancellation) */
    @JsonProperty("item_id")
    private Long itemId;
}
