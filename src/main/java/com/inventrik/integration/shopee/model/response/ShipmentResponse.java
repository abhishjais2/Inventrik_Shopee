package com.inventrik.integration.shopee.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for shipment / logistics Shopee APIs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentResponse {

    /** Shopee order serial number */
    @JsonProperty("order_sn")
    private String orderSn;

    /** Package number */
    @JsonProperty("package_number")
    private String packageNumber;

    /** Tracking number assigned by logistics */
    @JsonProperty("tracking_number")
    private String trackingNumber;

    /** Logistics status */
    @JsonProperty("logistics_status")
    private String logisticsStatus;
}
