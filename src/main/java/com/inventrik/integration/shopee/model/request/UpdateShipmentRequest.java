package com.inventrik.integration.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for updating shipment / initiating logistics on Shopee.
 * Maps to {@code POST /api/v2/logistics/ship_order}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateShipmentRequest {

    /** Shopee order serial number */
    @JsonProperty("order_sn")
    private String orderSn;

    /** Package number (for split orders) */
    @JsonProperty("package_number")
    private String packageNumber;

    /** Pickup address (for arrange-pickup flow) */
    @JsonProperty("pickup")
    private PickupInfo pickup;

    /** Dropoff info (for dropoff flow) */
    @JsonProperty("dropoff")
    private DropoffInfo dropoff;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PickupInfo {
        @JsonProperty("address_id")
        private Long addressId;

        @JsonProperty("pickup_time_id")
        private String pickupTimeId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DropoffInfo {
        @JsonProperty("branch_id")
        private Long branchId;

        @JsonProperty("tracking_no")
        private String trackingNo;
    }
}
