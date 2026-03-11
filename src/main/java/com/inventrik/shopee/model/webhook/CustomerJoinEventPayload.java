package com.inventrik.shopee.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Payload model for customer-join webhook events.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerJoinEventPayload {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("timestamp")
    private Long timestamp;

    public CustomerJoinEventPayload() {
    }

    public CustomerJoinEventPayload(Long userId, String username, Long timestamp) {
        this.userId = userId;
        this.username = username;
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
