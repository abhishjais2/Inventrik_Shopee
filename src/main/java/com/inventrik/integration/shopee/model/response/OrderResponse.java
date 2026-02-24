package com.inventrik.integration.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Response model for order detail / order list Shopee APIs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    /** Shopee order serial number */
    @JsonProperty("order_sn")
    private String orderSn;

    /** Order status */
    @JsonProperty("order_status")
    private String orderStatus;

    /** Currency code (e.g. MYR, SGD) */
    @JsonProperty("currency")
    private String currency;

    /** Total amount */
    @JsonProperty("total_amount")
    private Double totalAmount;

    /** Buyer user ID */
    @JsonProperty("buyer_user_id")
    private Long buyerUserId;

    /** Buyer username */
    @JsonProperty("buyer_username")
    private String buyerUsername;

    /** Shipping carrier */
    @JsonProperty("shipping_carrier")
    private String shippingCarrier;

    /** Order creation timestamp (Unix epoch) */
    @JsonProperty("create_time")
    private Long createTime;

    /** Last update timestamp (Unix epoch) */
    @JsonProperty("update_time")
    private Long updateTime;

    /** Items in the order */
    @JsonProperty("item_list")
    private List<Map<String, Object>> itemList;
}
