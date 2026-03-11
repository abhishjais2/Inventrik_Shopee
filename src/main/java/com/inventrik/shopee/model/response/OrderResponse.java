package com.inventrik.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Response model for order detail / order list Shopee APIs.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    @JsonProperty("order_sn")
    private String orderSn;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("buyer_user_id")
    private Long buyerUserId;

    @JsonProperty("buyer_username")
    private String buyerUsername;

    @JsonProperty("shipping_carrier")
    private String shippingCarrier;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("item_list")
    private List<Map<String, Object>> itemList;

    public OrderResponse() {
    }

    public OrderResponse(String orderSn, String orderStatus, String currency, Double totalAmount,
            Long buyerUserId, String buyerUsername, String shippingCarrier,
            Long createTime, Long updateTime, List<Map<String, Object>> itemList) {
        this.orderSn = orderSn;
        this.orderStatus = orderStatus;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.buyerUserId = buyerUserId;
        this.buyerUsername = buyerUsername;
        this.shippingCarrier = shippingCarrier;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.itemList = itemList;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(Long buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public String getShippingCarrier() {
        return shippingCarrier;
    }

    public void setShippingCarrier(String shippingCarrier) {
        this.shippingCarrier = shippingCarrier;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public List<Map<String, Object>> getItemList() {
        return itemList;
    }

    public void setItemList(List<Map<String, Object>> itemList) {
        this.itemList = itemList;
    }
}
