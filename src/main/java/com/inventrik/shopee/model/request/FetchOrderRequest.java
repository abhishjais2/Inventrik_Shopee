package com.inventrik.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request model for fetching order details from Shopee.
 * Maps to {@code GET /api/v2/order/get_order_detail}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchOrderRequest {

    @JsonProperty("order_sn_list")
    private List<String> orderSnList;

    @JsonProperty("response_optional_fields")
    private String responseOptionalFields;

    public FetchOrderRequest() {
    }

    public FetchOrderRequest(List<String> orderSnList, String responseOptionalFields) {
        this.orderSnList = orderSnList;
        this.responseOptionalFields = responseOptionalFields;
    }

    public List<String> getOrderSnList() {
        return orderSnList;
    }

    public void setOrderSnList(List<String> orderSnList) {
        this.orderSnList = orderSnList;
    }

    public String getResponseOptionalFields() {
        return responseOptionalFields;
    }

    public void setResponseOptionalFields(String responseOptionalFields) {
        this.responseOptionalFields = responseOptionalFields;
    }
}
