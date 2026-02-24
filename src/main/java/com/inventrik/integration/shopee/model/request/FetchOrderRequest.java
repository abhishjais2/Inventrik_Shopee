package com.inventrik.integration.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request model for fetching order details from Shopee.
 * Maps to {@code GET /api/v2/order/get_order_detail}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchOrderRequest {

    /** List of order serial numbers to fetch (max 50 per call) */
    @JsonProperty("order_sn_list")
    private List<String> orderSnList;

    /**
     * Optional comma-separated response fields:
     * e.g. "buyer_user_id,buyer_username,estimated_shipping_fee"
     */
    @JsonProperty("response_optional_fields")
    private String responseOptionalFields;
}
