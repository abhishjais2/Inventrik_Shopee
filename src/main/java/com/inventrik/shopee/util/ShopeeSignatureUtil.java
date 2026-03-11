package com.inventrik.shopee.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Utility for generating Shopee API request signatures.
 * <p>
 * Shopee requires every API call to include a {@code sign} query parameter
 * computed as {@code HMAC-SHA256(partnerKey, baseString)} where the base string
 * varies by API type:
 * <ul>
 * <li><b>Public API:</b> {@code partnerId + apiPath + timestamp}</li>
 * <li><b>Shop API:</b>
 * {@code partnerId + apiPath + timestamp + accessToken + shopId}</li>
 * <li><b>Merchant API:</b>
 * {@code partnerId + apiPath + timestamp + accessToken + merchantId}</li>
 * </ul>
 */
public final class ShopeeSignatureUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";

    private ShopeeSignatureUtil() {
        // Utility class — no instantiation
    }

    // ── Public API Signature ─────────────────────────────────────────────

    /**
     * Generate signature for a <b>public</b> Shopee API call (no access token
     * required).
     */
    public static String generateSignPublic(long partnerId, String apiPath, long timestamp, String partnerKey) {
        String baseString = partnerId + apiPath + timestamp;
        return hmacSha256(partnerKey, baseString);
    }

    // ── Shop API Signature ───────────────────────────────────────────────

    /**
     * Generate signature for a <b>shop-level</b> Shopee API call.
     */
    public static String generateSignShop(long partnerId, String apiPath, long timestamp,
            String accessToken, long shopId, String partnerKey) {
        String baseString = partnerId + apiPath + timestamp + accessToken + shopId;
        return hmacSha256(partnerKey, baseString);
    }

    // ── Merchant API Signature ───────────────────────────────────────────

    /**
     * Generate signature for a <b>merchant-level</b> Shopee API call.
     */
    public static String generateSignMerchant(long partnerId, String apiPath, long timestamp,
            String accessToken, long merchantId, String partnerKey) {
        String baseString = partnerId + apiPath + timestamp + accessToken + merchantId;
        return hmacSha256(partnerKey, baseString);
    }

    // ── Auth-endpoint Signature (token/get, access_token/get) ────────────

    /**
     * Generate signature for Shopee auth endpoints (/api/v2/auth/token/get, etc.).
     * These are public-type calls that only need partnerId + path + timestamp.
     */
    public static String generateSignAuth(long partnerId, String apiPath, long timestamp, String partnerKey) {
        return generateSignPublic(partnerId, apiPath, timestamp, partnerKey);
    }

    // ── HMAC-SHA256 Core ─────────────────────────────────────────────────

    private static String hmacSha256(String key, String data) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to compute HMAC-SHA256 signature", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
