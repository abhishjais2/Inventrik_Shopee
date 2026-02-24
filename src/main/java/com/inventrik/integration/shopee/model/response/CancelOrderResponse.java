package com.inventrik.integration.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for order cancellation Shopee API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderResponse {

    /** Shopee order serial number */
    @JsonProperty("order_sn")
    private String orderSn;

    /** Whether cancellation was accepted */
    @JsonProperty("cancel_result")
    private Boolean cancelResult;

    /** Timestamp of cancellation */
    @JsonProperty("update_time")
    private Long updateTime;
}
