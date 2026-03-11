package com.inventrik.shopee.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventrik.shopee.auth.ShopeeAuthService;
import com.inventrik.shopee.auth.TokenCacheManager;
import com.inventrik.shopee.config.ShopeeConfig;
import com.inventrik.shopee.constant.ShopeeConstants;
import com.inventrik.shopee.exception.ShopeeApiException;
import com.inventrik.shopee.model.request.CancelOrderRequest;
import com.inventrik.shopee.model.request.CreateOrderRequest;
import com.inventrik.shopee.model.request.FetchOrderRequest;
import com.inventrik.shopee.model.request.UpdateShipmentRequest;
import com.inventrik.shopee.model.response.*;
import com.inventrik.shopee.util.ShopeeSignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Single implementation of {@link ShopeeService} that handles all Shopee API
 * communication.
 * <p>
 * Key behaviour:
 * <ul>
 * <li>Automatically attaches access token, signature, and standard query
 * params</li>
 * <li>Centralized retry-on-401: clears token, refreshes, retries
 * <b>once</b></li>
 * <li>Structured logging for every API call (start → success/failure →
 * retry)</li>
 * <li>Designed for future AOP upgrade (retry/auth/logging can be
 * extracted)</li>
 * </ul>
 */
@Service
public class ShopeeServiceImpl implements ShopeeService {

    private static final Logger log = LoggerFactory.getLogger(ShopeeServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ShopeeAuthService authService;
    private final TokenCacheManager tokenCacheManager;
    private final ObjectMapper objectMapper;

    public ShopeeServiceImpl(RestTemplate restTemplate,
            ShopeeAuthService authService,
            TokenCacheManager tokenCacheManager,
            ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.authService = authService;
        this.tokenCacheManager = tokenCacheManager;
        this.objectMapper = objectMapper;
    }

    // ── ShopeeService Interface Methods ──────────────────────────────────

    @Override
    public List<OrderResponse> getOrderDetails(FetchOrderRequest request) {
        log.info("Fetching order details [orderSnList={}]", request.getOrderSnList());

        String orderSnCsv = String.join(",", request.getOrderSnList());

        ShopeeApiResponse<Map<String, Object>> response = executeWithRetry(
                ShopeeConstants.API_ORDER_GET_DETAIL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ShopeeApiResponse<Map<String, Object>>>() {
                },
                "order_sn_list", orderSnCsv);

        if (!response.isSuccess()) {
            throw new ShopeeApiException(0, response.getError(),
                    "Failed to fetch orders: " + response.getMessage(), response.getRequestId());
        }

        Map<String, Object> payload = response.getResponse();
        if (payload == null || !payload.containsKey("order_list")) {
            return Collections.emptyList();
        }

        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, OrderResponse.class);
        return objectMapper.convertValue(payload.get("order_list"), listType);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating order on Shopee");

        ShopeeApiResponse<OrderResponse> response = executeWithRetry(
                ShopeeConstants.API_ORDER_GET_LIST,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ShopeeApiResponse<OrderResponse>>() {
                });

        if (!response.isSuccess()) {
            throw new ShopeeApiException(0, response.getError(),
                    "Failed to create order: " + response.getMessage(), response.getRequestId());
        }
        return response.getResponse();
    }

    @Override
    public ShipmentResponse updateShipment(UpdateShipmentRequest request) {
        log.info("Updating shipment [orderSn={}]", request.getOrderSn());

        ShopeeApiResponse<ShipmentResponse> response = executeWithRetry(
                ShopeeConstants.API_LOGISTICS_SHIP_ORDER,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ShopeeApiResponse<ShipmentResponse>>() {
                });

        if (!response.isSuccess()) {
            throw new ShopeeApiException(0, response.getError(),
                    "Failed to update shipment: " + response.getMessage(), response.getRequestId());
        }
        return response.getResponse();
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest request) {
        log.info("Cancelling order [orderSn={}]", request.getOrderSn());

        ShopeeApiResponse<CancelOrderResponse> response = executeWithRetry(
                ShopeeConstants.API_ORDER_CANCEL,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ShopeeApiResponse<CancelOrderResponse>>() {
                });

        if (!response.isSuccess()) {
            throw new ShopeeApiException(0, response.getError(),
                    "Failed to cancel order: " + response.getMessage(), response.getRequestId());
        }
        return response.getResponse();
    }

    // ── Centralized Execute-With-Retry ───────────────────────────────────

    private <T> T executeWithRetry(String apiPath, HttpMethod method, Object body,
            ParameterizedTypeReference<T> responseType,
            String... extraQueryParams) {
        long shopId = ShopeeConfig.SHOP_ID;

        try {
            return executeCall(apiPath, method, body, responseType, shopId, extraQueryParams);
        } catch (HttpClientErrorException.Unauthorized ex) {
            log.warn("Received 401 Unauthorized from Shopee [apiPath={}], attempting token refresh and retry", apiPath);
        } catch (ShopeeApiException ex) {
            if (ex.getHttpStatus() == 401) {
                log.warn("Received 401 from Shopee [apiPath={}], attempting token refresh and retry", apiPath);
            } else {
                throw ex;
            }
        }

        tokenCacheManager.clearToken(shopId);
        try {
            authService.refreshAccessToken(shopId);
        } catch (Exception refreshEx) {
            log.error("Token refresh failed after 401 [apiPath={}]: {}", apiPath, refreshEx.getMessage());
            throw new ShopeeApiException("Token refresh failed after 401. Re-authorization may be required.",
                    refreshEx);
        }

        log.info("Retrying API call after token refresh [apiPath={}]", apiPath);
        try {
            return executeCall(apiPath, method, body, responseType, shopId, extraQueryParams);
        } catch (Exception retryEx) {
            log.error("Retry failed [apiPath={}]: {}", apiPath, retryEx.getMessage());
            throw new ShopeeApiException("API call failed after retry: " + retryEx.getMessage(), retryEx);
        }
    }

    private <T> T executeCall(String apiPath, HttpMethod method, Object body,
            ParameterizedTypeReference<T> responseType,
            long shopId, String... extraQueryParams) {
        String accessToken = authService.ensureValidAccessToken(shopId);
        long timestamp = System.currentTimeMillis() / 1000;

        String sign = ShopeeSignatureUtil.generateSignShop(
                ShopeeConfig.PARTNER_ID, apiPath, timestamp,
                accessToken, shopId, ShopeeConfig.PARTNER_KEY);

        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(ShopeeConfig.BASE_URL + apiPath)
                .queryParam("partner_id", ShopeeConfig.PARTNER_ID)
                .queryParam("timestamp", timestamp)
                .queryParam("access_token", accessToken)
                .queryParam("shop_id", shopId)
                .queryParam("sign", sign);

        for (int i = 0; i < extraQueryParams.length - 1; i += 2) {
            urlBuilder.queryParam(extraQueryParams[i], extraQueryParams[i + 1]);
        }

        String url = urlBuilder.build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        log.debug("Shopee API call START [method={}, path={}]", method, apiPath);

        ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, entity, responseType);

        log.debug("Shopee API call SUCCESS [method={}, path={}, httpStatus={}]",
                method, apiPath, responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    // ── Sandbox Shop Authorization URL Generator ─────────────────────────────

    @Override
    public String generateSandboxAuthUrl() {
        long timestamp = System.currentTimeMillis() / 1000;
        String apiPath = "/api/v2/shop/auth_partner";

        String baseString = ShopeeConfig.PARTNER_ID + apiPath + timestamp;
        String sign = ShopeeSignatureUtil.generateSignPublic(
                ShopeeConfig.PARTNER_ID,
                apiPath,
                timestamp,
                ShopeeConfig.PARTNER_KEY);

        log.info("Auth URL Debug => baseString: [{}]", baseString);
        log.info("Auth URL Debug => sign: [{}] (length={})", sign, sign.length());
        log.info("Auth URL Debug => partnerKey first 8 chars: [{}]",
                ShopeeConfig.PARTNER_KEY.substring(0, 8));

        String url = ShopeeConfig.BASE_URL + apiPath
                + "?partner_id=" + ShopeeConfig.PARTNER_ID
                + "&timestamp=" + timestamp
                + "&sign=" + sign
                + "&redirect="
                + java.net.URLEncoder.encode(ShopeeConfig.REDIRECT_URL, java.nio.charset.StandardCharsets.UTF_8);

        log.info("Generated sandbox auth URL: {}", url);
        return url;
    }
}
