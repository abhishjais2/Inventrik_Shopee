package com.inventrik.shopee.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request model for updating shipment / initiating logistics on Shopee.
 * Maps to {@code POST /api/v2/logistics/ship_order}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateShipmentRequest {

    @JsonProperty("order_sn")
    private String orderSn;

    @JsonProperty("package_number")
    private String packageNumber;

    @JsonProperty("pickup")
    private PickupInfo pickup;

    @JsonProperty("dropoff")
    private DropoffInfo dropoff;

    public UpdateShipmentRequest() {
    }

    public UpdateShipmentRequest(String orderSn, String packageNumber, PickupInfo pickup, DropoffInfo dropoff) {
        this.orderSn = orderSn;
        this.packageNumber = packageNumber;
        this.pickup = pickup;
        this.dropoff = dropoff;
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

    public PickupInfo getPickup() {
        return pickup;
    }

    public void setPickup(PickupInfo pickup) {
        this.pickup = pickup;
    }

    public DropoffInfo getDropoff() {
        return dropoff;
    }

    public void setDropoff(DropoffInfo dropoff) {
        this.dropoff = dropoff;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PickupInfo {
        @JsonProperty("address_id")
        private Long addressId;

        @JsonProperty("pickup_time_id")
        private String pickupTimeId;

        public PickupInfo() {
        }

        public PickupInfo(Long addressId, String pickupTimeId) {
            this.addressId = addressId;
            this.pickupTimeId = pickupTimeId;
        }

        public Long getAddressId() {
            return addressId;
        }

        public void setAddressId(Long addressId) {
            this.addressId = addressId;
        }

        public String getPickupTimeId() {
            return pickupTimeId;
        }

        public void setPickupTimeId(String pickupTimeId) {
            this.pickupTimeId = pickupTimeId;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DropoffInfo {
        @JsonProperty("branch_id")
        private Long branchId;

        @JsonProperty("tracking_no")
        private String trackingNo;

        public DropoffInfo() {
        }

        public DropoffInfo(Long branchId, String trackingNo) {
            this.branchId = branchId;
            this.trackingNo = trackingNo;
        }

        public Long getBranchId() {
            return branchId;
        }

        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }

        public String getTrackingNo() {
            return trackingNo;
        }

        public void setTrackingNo(String trackingNo) {
            this.trackingNo = trackingNo;
        }
    }
}
