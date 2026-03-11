package com.inventrik.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Payload model for order-related webhook events (push code 3 —
 * order_status_push).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderEventPayload {

    @JsonProperty("ordersn")
    private String orderSn;

    @JsonProperty("status")
    private String status;

    @JsonProperty("update_time")
    private Long updateTime;

    public OrderEventPayload() {
    }

    public OrderEventPayload(String orderSn, String status, Long updateTime) {
        this.orderSn = orderSn;
        this.status = status;
        this.updateTime = updateTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
