package com.inventrik.shopee.controller;

import com.inventrik.shopee.model.webhook.ShopeeWebhookRequest;
import com.inventrik.shopee.service.ShopeeWebhookProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Single entry point for all Shopee webhook push notifications.
 * <p>
 * This controller is intentionally thin — it accepts the payload and
 * immediately delegates to {@link ShopeeWebhookProcessor} for routing.
 * No business logic belongs here.
 */
@RestController
@RequestMapping("/ims/shopee")
public class ShopeeWebhookController {

    private static final Logger log = LoggerFactory.getLogger(ShopeeWebhookController.class);

    private final ShopeeWebhookProcessor webhookProcessor;

    public ShopeeWebhookController(ShopeeWebhookProcessor webhookProcessor) {
        this.webhookProcessor = webhookProcessor;
    }

    /**
     * Receive Shopee webhook events.
     * <p>
     * Shopee expects a 200 OK response; any other status may trigger re-delivery.
     *
     * @param request the webhook payload from Shopee
     * @return 200 OK
     */
    @PostMapping("/events.json")
    public ResponseEntity<String> handleWebhookEvent(@RequestBody ShopeeWebhookRequest request) {
        log.info("Webhook received [eventType={}, code={}, shopId={}]",
                request.getEventType(), request.getCode(), request.getShopId());

        webhookProcessor.process(request);

        return ResponseEntity.ok("OK");
    }
}
