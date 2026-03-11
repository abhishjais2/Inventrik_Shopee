package com.inventrik.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model for order cancellation Shopee API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderResponse {

    @JsonProperty("order_sn")
    private String orderSn;

    @JsonProperty("cancel_result")
    private Boolean cancelResult;

    @JsonProperty("update_time")
    private Long updateTime;

    public CancelOrderResponse() {
    }

    public CancelOrderResponse(String orderSn, Boolean cancelResult, Long updateTime) {
        this.orderSn = orderSn;
        this.cancelResult = cancelResult;
        this.updateTime = updateTime;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Boolean getCancelResult() {
        return cancelResult;
    }

    public void setCancelResult(Boolean cancelResult) {
        this.cancelResult = cancelResult;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
