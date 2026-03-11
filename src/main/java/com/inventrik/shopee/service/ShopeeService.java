package com.inventrik.shopee.service;

<<<<<<< HEAD
=======
import com.inventrik.shopee.model.ShopConnectionStatus;
>>>>>>> 2ff3c00 (included uninstall features too)
import com.inventrik.shopee.model.request.CancelOrderRequest;
import com.inventrik.shopee.model.request.CreateOrderRequest;
import com.inventrik.shopee.model.request.FetchOrderRequest;
import com.inventrik.shopee.model.request.UpdateShipmentRequest;
import com.inventrik.shopee.model.response.CancelOrderResponse;
import com.inventrik.shopee.model.response.OrderResponse;
import com.inventrik.shopee.model.response.ShipmentResponse;

import java.util.List;

/**
 * Abstraction for all external Shopee API operations.
 * <p>
 * Each Shopee API has a dedicated method. Implementations handle
 * authentication, signing, serialization, and retry logic transparently.
 */
public interface ShopeeService {

    List<OrderResponse> getOrderDetails(FetchOrderRequest request);

    OrderResponse createOrder(CreateOrderRequest request);

    ShipmentResponse updateShipment(UpdateShipmentRequest request);

    CancelOrderResponse cancelOrder(CancelOrderRequest request);

<<<<<<< HEAD
=======
    /** Generate the install URL for merchants to authorize VoucherMatic. */
    String generateInstallUrl();

    /** Disconnect a shop from VoucherMatic (clear tokens, mark as disconnected). */
    ShopConnectionStatus uninstallShop(long shopId);

>>>>>>> 2ff3c00 (included uninstall features too)
    String generateSandboxAuthUrl();
}
