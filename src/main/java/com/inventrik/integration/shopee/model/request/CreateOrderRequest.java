package com.inventrik.integration.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest {

    /** Recipient address information */
    @JsonProperty("address")
    private Map<String, Object> address;

    /** List of items in the order */
    @JsonProperty("items")
    private List<Map<String, Object>> items;

    /** Shipping method / logistics channel ID */
    @JsonProperty("logistics_channel_id")
    private Long logisticsChannelId;

    /** Remark / note for the order */
    @JsonProperty("remark")
    private String remark;
}
