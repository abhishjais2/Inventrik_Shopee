package com.inventrik.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request model for cancelling an order on Shopee.
 * Maps to {@code POST /api/v2/order/cancel_order}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderRequest {

    @JsonProperty("order_sn")
    private String orderSn;

    @JsonProperty("cancel_reason")
    private String cancelReason;

    @JsonProperty("item_id")
    private Long itemId;

    public CancelOrderRequest() {
    }

    public CancelOrderRequest(String orderSn, String cancelReason, Long itemId) {
        this.orderSn = orderSn;
        this.cancelReason = cancelReason;
        this.itemId = itemId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
