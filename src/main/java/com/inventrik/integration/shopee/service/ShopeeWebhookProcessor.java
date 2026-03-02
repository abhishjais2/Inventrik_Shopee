package com.inventrik.integration.shopee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventrik.integration.shopee.constant.ShopeeConstants;
import com.inventrik.integration.shopee.model.webhook.CustomerJoinEventPayload;
import com.inventrik.integration.shopee.model.webhook.OrderEventPayload;
import com.inventrik.integration.shopee.model.webhook.PaymentEventPayload;
import com.inventrik.integration.shopee.model.webhook.ShopeeWebhookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

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

    public ShopeeWebhookProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Main entry point — dispatches the webhook to the appropriate handler based on
     * event type.
     *
     * @param request the incoming webhook payload
     */
    public void process(ShopeeWebhookRequest request) {
        String eventType = resolveEventType(request);

        log.info("Processing webhook event [eventType={}, code={}, shopId={}, timestamp={}]",
                eventType, request.getCode(), request.getShopId(), request.getTimestamp());

        switch (eventType) {
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

    /**
     * Handle order status change events (push code 3).
     */
    private void handleOrderEvent(JsonNode data) {
        OrderEventPayload payload = objectMapper.convertValue(data, OrderEventPayload.class);
        log.info("Order event received [orderSn={}, status={}, updateTime={}]",
                payload.getOrderSn(), payload.getStatus(), payload.getUpdateTime());

        // No business logic — this is a communication layer only.
        // The consuming system will implement its own handler by overriding or
        // listening.
    }

    /**
     * Handle payment-related events.
     */
    private void handlePaymentEvent(JsonNode data) {
        PaymentEventPayload payload = objectMapper.convertValue(data, PaymentEventPayload.class);
        log.info("Payment event received [orderSn={}, paymentStatus={}, method={}]",
                payload.getOrderSn(), payload.getPaymentStatus(), payload.getPaymentMethod());
    }

    /**
     * Handle customer join events.
     */
    private void handleCustomerJoinEvent(JsonNode data) {
        CustomerJoinEventPayload payload = objectMapper.convertValue(data, CustomerJoinEventPayload.class);
        log.info("Customer join event received [userId={}, username={}]",
                payload.getUserId(), payload.getUsername());
    }

    /**
     * Handle unknown / future event types — logs a warning but does not throw.
     */
    private void handleUnknownEvent(String eventType, JsonNode data) {
        log.warn("Unknown webhook event type received [eventType={}]. Payload: {}", eventType, data);
    }

    // ── Internals ────────────────────────────────────────────────────────

    /**
     * Resolve the event type string — uses the {@code event_type} field first,
     * falling back to mapping the numeric {@code code} to a known event type.
     */
    private String resolveEventType(ShopeeWebhookRequest request) {
        // Prefer explicit event_type string
        if (request.getEventType() != null && !request.getEventType().isEmpty()) {
            return request.getEventType().toUpperCase();
        }

        // Fall back to numeric push code mapping
        if (request.getCode() != null) {
            switch (request.getCode()) {
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
