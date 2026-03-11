package com.inventrik.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Payload model for payment-related webhook events.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEventPayload {

    @JsonProperty("ordersn")
    private String orderSn;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("update_time")
    private Long updateTime;

    public PaymentEventPayload() {
    }

    public PaymentEventPayload(String orderSn, String paymentStatus, String paymentMethod, Long updateTime) {
        this.orderSn = orderSn;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.updateTime = updateTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
