package com.inventrik.shopee.controller;

import com.inventrik.shopee.auth.ShopeeAuthService;
import com.inventrik.shopee.model.auth.ShopeeTokenInfo;
import com.inventrik.shopee.model.request.CancelOrderRequest;
import com.inventrik.shopee.model.request.CreateOrderRequest;
import com.inventrik.shopee.model.request.FetchOrderRequest;
import com.inventrik.shopee.model.request.UpdateShipmentRequest;
import com.inventrik.shopee.model.response.CancelOrderResponse;
import com.inventrik.shopee.model.response.OrderResponse;
import com.inventrik.shopee.model.response.ShipmentResponse;
import com.inventrik.shopee.service.ShopeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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

    @PostMapping("/auth/token")
    public ResponseEntity<ShopeeTokenInfo> getToken(@RequestBody Map<String, Object> body) {
        String code = (String) body.get("code");
        long shopId = ((Number) body.get("shop_id")).longValue();
        log.info("Test: exchanging auth code for token [shopId={}]", shopId);
        ShopeeTokenInfo tokenInfo = authService.getAccessToken(code, shopId);
        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ShopeeTokenInfo> refreshToken(@RequestParam long shopId) {
        log.info("Test: refreshing token [shopId={}]", shopId);
        ShopeeTokenInfo tokenInfo = authService.refreshAccessToken(shopId);
        return ResponseEntity.ok(tokenInfo);
    }

    // ── Order Testing ────────────────────────────────────────────────────

    @PostMapping("/order/fetch")
    public ResponseEntity<List<OrderResponse>> fetchOrders(@RequestBody FetchOrderRequest request) {
        log.info("Test: fetching orders");
        List<OrderResponse> orders = shopeeService.getOrderDetails(request);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Test: creating order");
        OrderResponse order = shopeeService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/order/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@RequestBody CancelOrderRequest request) {
        log.info("Test: cancelling order [orderSn={}]", request.getOrderSn());
        CancelOrderResponse result = shopeeService.cancelOrder(request);
        return ResponseEntity.ok(result);
    }

    // ── Shipment Testing ─────────────────────────────────────────────────

    @PostMapping("/shipment/update")
    public ResponseEntity<ShipmentResponse> updateShipment(@RequestBody UpdateShipmentRequest request) {
        log.info("Test: updating shipment [orderSn={}]", request.getOrderSn());
        ShipmentResponse result = shopeeService.updateShipment(request);
        return ResponseEntity.ok(result);
    }

    // ── Generate Sandbox Auth URL ────────────────────────────────────────────

    @GetMapping(value = "/auth/url", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateAuthUrl() {
        log.info("Generating sandbox authorization URL");
        String url = shopeeService.generateSandboxAuthUrl();
        return ResponseEntity.ok(url);
    }
}
