package com.inventrik.integration.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload model for payment-related webhook events.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEventPayload {

    /** Shopee order serial number */
    @JsonProperty("ordersn")
    private String orderSn;

    /** Payment status */
    @JsonProperty("payment_status")
    private String paymentStatus;

    /** Payment method used */
    @JsonProperty("payment_method")
    private String paymentMethod;

    /** Unix timestamp of the payment event */
    @JsonProperty("update_time")
    private Long updateTime;
}
