package com.inventrik.integration.shopee.service;

import com.inventrik.integration.shopee.model.request.CancelOrderRequest;
import com.inventrik.integration.shopee.model.request.CreateOrderRequest;
import com.inventrik.integration.shopee.model.request.FetchOrderRequest;
import com.inventrik.integration.shopee.model.request.UpdateShipmentRequest;
import com.inventrik.integration.shopee.model.response.CancelOrderResponse;
import com.inventrik.integration.shopee.model.response.OrderResponse;
import com.inventrik.integration.shopee.model.response.ShipmentResponse;

import java.util.List;

/**
 * Abstraction for all external Shopee API operations.
 * <p>
 * Each Shopee API has a dedicated method. Implementations handle
 * authentication, signing, serialization, and retry logic transparently.
 */
public interface ShopeeService {

    /**
     * Fetch order details from Shopee.
     *
     * @param request contains the order serial numbers and optional fields
     * @return list of order details
     */
    List<OrderResponse> getOrderDetails(FetchOrderRequest request);

    /**
     * Create an order on Shopee (conceptual — typically buyer-initiated).
     *
     * @param request the order creation payload
     * @return the created order details
     */
    OrderResponse createOrder(CreateOrderRequest request);

    /**
     * Update shipment / initiate logistics for an order.
     *
     * @param request the shipment update payload
     * @return the shipment status
     */
    ShipmentResponse updateShipment(UpdateShipmentRequest request);

    /**
     * Cancel an order on Shopee.
     *
     * @param request the cancellation payload
     * @return the cancellation result
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request);
}
