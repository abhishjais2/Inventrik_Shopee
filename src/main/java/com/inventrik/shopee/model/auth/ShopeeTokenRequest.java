package com.inventrik.shopee.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body for Shopee token endpoints ({@code /api/v2/auth/token/get}
 * and {@code /api/v2/auth/access_token/get}).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeTokenRequest {

    @JsonProperty("code")
    private String code;

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("partner_id")
    private Long partnerId;

    @JsonProperty("refresh_token")
    private String refreshToken;

    public ShopeeTokenRequest() {
    }

    public ShopeeTokenRequest(String code, Long shopId, Long partnerId, String refreshToken) {
        this.code = code;
        this.shopId = shopId;
        this.partnerId = partnerId;
        this.refreshToken = refreshToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
