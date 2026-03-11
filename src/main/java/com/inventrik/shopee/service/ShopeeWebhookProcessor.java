package com.inventrik.shopee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
=======
import com.inventrik.shopee.auth.TokenCacheManager;
>>>>>>> 2ff3c00 (included uninstall features too)
import com.inventrik.shopee.constant.ShopeeConstants;
import com.inventrik.shopee.model.webhook.CustomerJoinEventPayload;
import com.inventrik.shopee.model.webhook.OrderEventPayload;
import com.inventrik.shopee.model.webhook.PaymentEventPayload;
import com.inventrik.shopee.model.webhook.ShopeeWebhookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Single webhook processor that routes incoming Shopee events to dedicated
 * handler methods.
 * <p>
 * <b>Extensibility:</b> To handle a new event type, simply:
 * <ol>
 * <li>Add a constant in {@link ShopeeConstants}</li>
 * <li>Add a case in the switch + a handler method here</li>
 * </ol>
 * No controller changes needed.
 */
@Service
public class ShopeeWebhookProcessor {

    private static final Logger log = LoggerFactory.getLogger(ShopeeWebhookProcessor.class);

    private final ObjectMapper objectMapper;
    private final TokenCacheManager tokenCacheManager;

    public ShopeeWebhookProcessor(ObjectMapper objectMapper, TokenCacheManager tokenCacheManager) {
        this.objectMapper = objectMapper;
        this.tokenCacheManager = tokenCacheManager;
    }

    public void process(ShopeeWebhookRequest request) {
        String eventType = resolveEventType(request);

        log.info("Processing webhook event [eventType={}, code={}, shopId={}, timestamp={}]",
                eventType, request.getCode(), request.getShopId(), request.getTimestamp());

        switch (eventType) {
            case ShopeeConstants.EVENT_SHOP_DEAUTH:
                handleShopDeauthEvent(request.getShopId(), request.getData());
                break;

            case ShopeeConstants.EVENT_ORDER_STATUS:
                handleOrderEvent(request.getData());
                break;

            case ShopeeConstants.EVENT_PAYMENT_UPDATE:
                handlePaymentEvent(request.getData());
                break;

            case ShopeeConstants.EVENT_CUSTOMER_JOIN:
                handleCustomerJoinEvent(request.getData());
                break;

            default:
                handleUnknownEvent(eventType, request.getData());
                break;
        }
    }

    // ── Event Handlers ───────────────────────────────────────────────────

<<<<<<< HEAD
=======
    private void handleShopDeauthEvent(Long shopId, JsonNode data) {
        log.warn("Shop deauthorization received [shopId={}]. Clearing cached tokens.", shopId);
        if (shopId != null) {
            tokenCacheManager.clearToken(shopId);
        }
        log.warn("Shop deauthorized — app uninstalled by seller [shopId={}, data={}]", shopId, data);
    }

>>>>>>> 2ff3c00 (included uninstall features too)
    private void handleOrderEvent(JsonNode data) {
        OrderEventPayload payload = objectMapper.convertValue(data, OrderEventPayload.class);
        log.info("Order event received [orderSn={}, status={}, updateTime={}]",
                payload.getOrderSn(), payload.getStatus(), payload.getUpdateTime());
    }

    private void handlePaymentEvent(JsonNode data) {
        PaymentEventPayload payload = objectMapper.convertValue(data, PaymentEventPayload.class);
        log.info("Payment event received [orderSn={}, paymentStatus={}, method={}]",
                payload.getOrderSn(), payload.getPaymentStatus(), payload.getPaymentMethod());
    }

    private void handleCustomerJoinEvent(JsonNode data) {
        CustomerJoinEventPayload payload = objectMapper.convertValue(data, CustomerJoinEventPayload.class);
        log.info("Customer join event received [userId={}, username={}]",
                payload.getUserId(), payload.getUsername());
    }

    private void handleUnknownEvent(String eventType, JsonNode data) {
        log.warn("Unknown webhook event type received [eventType={}]. Payload: {}", eventType, data);
    }

    // ── Internals ────────────────────────────────────────────────────────

    private String resolveEventType(ShopeeWebhookRequest request) {
        if (request.getEventType() != null && !request.getEventType().isEmpty()) {
            return request.getEventType().toUpperCase();
        }

        if (request.getCode() != null) {
            switch (request.getCode()) {
                case ShopeeConstants.PUSH_CODE_SHOP_DEAUTH:
                    return ShopeeConstants.EVENT_SHOP_DEAUTH;
                case ShopeeConstants.PUSH_CODE_ORDER_STATUS:
                    return ShopeeConstants.EVENT_ORDER_STATUS;
                case ShopeeConstants.PUSH_CODE_PAYMENT_UPDATE:
                    return ShopeeConstants.EVENT_PAYMENT_UPDATE;
                case ShopeeConstants.PUSH_CODE_CUSTOMER_JOIN:
                    return ShopeeConstants.EVENT_CUSTOMER_JOIN;
                default:
                    return "UNKNOWN_CODE_" + request.getCode();
            }
        }

        return "UNKNOWN";
    }
}
