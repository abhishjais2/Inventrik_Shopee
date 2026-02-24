package com.inventrik.integration.shopee.controller;

import com.inventrik.integration.shopee.auth.ShopeeAuthService;
import com.inventrik.integration.shopee.model.auth.ShopeeTokenInfo;
import com.inventrik.integration.shopee.model.request.CancelOrderRequest;
import com.inventrik.integration.shopee.model.request.CreateOrderRequest;
import com.inventrik.integration.shopee.model.request.FetchOrderRequest;
import com.inventrik.integration.shopee.model.request.UpdateShipmentRequest;
import com.inventrik.integration.shopee.model.response.CancelOrderResponse;
import com.inventrik.integration.shopee.model.response.OrderResponse;
import com.inventrik.integration.shopee.model.response.ShipmentResponse;
import com.inventrik.integration.shopee.service.ShopeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <b>Development-only</b> controller for testing Shopee service methods via
 * Postman.
 * <p>
 * This controller should NOT be deployed to production. It provides direct
 * access to all {@link ShopeeService} methods for local testing and debugging.
 */
@RestController
@RequestMapping("/test/shopee")
public class ShopeeTestController {

    private static final Logger log = LoggerFactory.getLogger(ShopeeTestController.class);

    private final ShopeeService shopeeService;
    private final ShopeeAuthService authService;

    public ShopeeTestController(ShopeeService shopeeService, ShopeeAuthService authService) {
        this.shopeeService = shopeeService;
        this.authService = authService;
    }

    // ── Auth Testing ─────────────────────────────────────────────────────

    /**
     * Test: exchange an auth code for tokens.
     * <p>
     * POST /test/shopee/auth/token
     * Body: { "code": "...", "shop_id": 12345 }
     */
    @PostMapping("/auth/token")
    public ResponseEntity<ShopeeTokenInfo> getToken(@RequestBody Map<String, Object> body) {
        String code = (String) body.get("code");
        long shopId = ((Number) body.get("shop_id")).longValue();
        log.info("Test: exchanging auth code for token [shopId={}]", shopId);
        ShopeeTokenInfo tokenInfo = authService.getAccessToken(code, shopId);
        return ResponseEntity.ok(tokenInfo);
    }

    /**
     * Test: manually trigger token refresh.
     * <p>
     * POST /test/shopee/auth/refresh?shopId=12345
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<ShopeeTokenInfo> refreshToken(@RequestParam long shopId) {
        log.info("Test: refreshing token [shopId={}]", shopId);
        ShopeeTokenInfo tokenInfo = authService.refreshAccessToken(shopId);
        return ResponseEntity.ok(tokenInfo);
    }

    // ── Order Testing ────────────────────────────────────────────────────

    /**
     * Test: fetch order details.
     * <p>
     * POST /test/shopee/order/fetch
     * Body: { "order_sn_list": ["ORDER001", "ORDER002"] }
     */
    @PostMapping("/order/fetch")
    public ResponseEntity<List<OrderResponse>> fetchOrders(@RequestBody FetchOrderRequest request) {
        log.info("Test: fetching orders");
        List<OrderResponse> orders = shopeeService.getOrderDetails(request);
        return ResponseEntity.ok(orders);
    }

    /**
     * Test: create order.
     * <p>
     * POST /test/shopee/order/create
     * Body: { ... CreateOrderRequest fields ... }
     */
    @PostMapping("/order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Test: creating order");
        OrderResponse order = shopeeService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    /**
     * Test: cancel order.
     * <p>
     * POST /test/shopee/order/cancel
     * Body: { "order_sn": "...", "cancel_reason": "..." }
     */
    @PostMapping("/order/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@RequestBody CancelOrderRequest request) {
        log.info("Test: cancelling order [orderSn={}]", request.getOrderSn());
        CancelOrderResponse result = shopeeService.cancelOrder(request);
        return ResponseEntity.ok(result);
    }

    // ── Shipment Testing ─────────────────────────────────────────────────

    /**
     * Test: update shipment.
     * <p>
     * POST /test/shopee/shipment/update
     * Body: { "order_sn": "...", ... }
     */
    @PostMapping("/shipment/update")
    public ResponseEntity<ShipmentResponse> updateShipment(@RequestBody UpdateShipmentRequest request) {
        log.info("Test: updating shipment [orderSn={}]", request.getOrderSn());
        ShipmentResponse result = shopeeService.updateShipment(request);
        return ResponseEntity.ok(result);
    }
}
