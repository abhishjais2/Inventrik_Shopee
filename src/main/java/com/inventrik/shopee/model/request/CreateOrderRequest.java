package com.inventrik.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Request model for creating an order on Shopee.
 * <p>
 * Note: Shopee's order creation is typically initiated by buyers on the
 * platform.
 * This model represents the conceptual "create order" payload for future API
 * support.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest {

    @JsonProperty("address")
    private Map<String, Object> address;

    @JsonProperty("items")
    private List<Map<String, Object>> items;

    @JsonProperty("logistics_channel_id")
    private Long logisticsChannelId;

    @JsonProperty("remark")
    private String remark;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Map<String, Object> address, List<Map<String, Object>> items, Long logisticsChannelId,
            String remark) {
        this.address = address;
        this.items = items;
        this.logisticsChannelId = logisticsChannelId;
        this.remark = remark;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public Long getLogisticsChannelId() {
        return logisticsChannelId;
    }

    public void setLogisticsChannelId(Long logisticsChannelId) {
        this.logisticsChannelId = logisticsChannelId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
