package com.inventrik.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model for shipment / logistics Shopee APIs.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentResponse {

    @JsonProperty("order_sn")
    private String orderSn;

    @JsonProperty("package_number")
    private String packageNumber;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("logistics_status")
    private String logisticsStatus;

    public ShipmentResponse() {
    }

    public ShipmentResponse(String orderSn, String packageNumber, String trackingNumber, String logisticsStatus) {
        this.orderSn = orderSn;
        this.packageNumber = packageNumber;
        this.trackingNumber = trackingNumber;
        this.logisticsStatus = logisticsStatus;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }
}
