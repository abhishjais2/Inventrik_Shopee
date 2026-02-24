package com.inventrik.integration.shopee.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for Shopee token endpoints ({@code /api/v2/auth/token/get}
 * and {@code /api/v2/auth/access_token/get}).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopeeTokenRequest {

    /** Authorization code received from Shopee OAuth callback */
    @JsonProperty("code")
    private String code;

    /** Shop ID to get token for */
    @JsonProperty("shop_id")
    private Long shopId;

    /** Partner ID */
    @JsonProperty("partner_id")
    private Long partnerId;

    /** Refresh token (used only for token refresh calls) */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
