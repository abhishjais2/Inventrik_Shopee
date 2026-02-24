package com.inventrik.integration.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload model for customer-join webhook events.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerJoinEventPayload {

    /** Shopee user ID of the new follower/customer */
    @JsonProperty("user_id")
    private Long userId;

    /** Username of the customer */
    @JsonProperty("username")
    private String username;

    /** Unix timestamp of the join event */
    @JsonProperty("timestamp")
    private Long timestamp;
}
