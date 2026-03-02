# Shopee Integration Library

A Spring Boot SDK for integrating with the Shopee Open Platform API.

## What This Project Does

- **OAuth Authentication** — Handles token exchange, caching, and automatic refresh
- **API Request Signing** — Generates HMAC-SHA256 signatures required by Shopee
- **Order Operations** — Fetch, create, cancel orders via Shopee API
- **Shipment Management** — Update logistics and shipping status
- **Webhook Processing** — Receives and routes real-time events from Shopee (order status, payments, etc.)
- **Error Handling** — Centralized exception handling with structured JSON responses
- **Retry Logic** — Automatic retry on 401 Unauthorized with token refresh

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Maven
- Lombok
- Jackson (JSON)

## Project Structure

```
src/main/java/com/inventrik/integration/shopee/
├── auth/           # Token management (ShopeeAuthService, TokenCacheManager)
├── config/         # Configuration (ShopeeConfig, RestTemplateConfig)
├── controller/     # HTTP endpoints (Webhook, Test)
├── service/        # Core logic (ShopeeService, WebhookProcessor)
├── model/          # DTOs (request, response, webhook, auth)
├── exception/      # Error handling
├── util/           # Signature generation
└── constant/       # API paths, event types
```

## Prerequisites

1. Java 17+
2. Maven 3.6+
3. Shopee Partner Account with:
   - Partner ID
   - Partner Key (HMAC secret)
   - Shop ID

## Configuration

Update credentials in `src/main/java/.../config/ShopeeConfig.java`:

```java
public static final long PARTNER_ID = YOUR_PARTNER_ID;
public static final String PARTNER_KEY = "YOUR_PARTNER_KEY";
public static final long SHOP_ID = YOUR_SHOP_ID;
```

Switch between sandbox and production:

```java
public static final String BASE_URL = BASE_URL_SANDBOX;  // or BASE_URL_PRODUCTION
```

## How to Run

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

The server starts on `http://localhost:8080`

### Run as JAR

```bash
java -jar target/shopee-integration-lib-1.0.0-SNAPSHOT.jar
```

## API Endpoints

### Webhook (for Shopee to call)

```
POST /ims/shopee/events.json
```

### Test Endpoints (development only)

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/test/shopee/auth/token` | POST | Exchange auth code for tokens |
| `/test/shopee/auth/refresh` | POST | Refresh access token |
| `/test/shopee/order/fetch` | POST | Fetch order details |
| `/test/shopee/order/create` | POST | Create order |
| `/test/shopee/order/cancel` | POST | Cancel order |
| `/test/shopee/shipment/update` | POST | Update shipment |

## Example Usage

### Fetch Orders (via Postman)

```http
POST http://localhost:8080/test/shopee/order/fetch
Content-Type: application/json

{
  "order_sn_list": ["ORDER123", "ORDER456"]
}
```

### Exchange Auth Code for Token

```http
POST http://localhost:8080/test/shopee/auth/token
Content-Type: application/json

{
  "code": "auth_code_from_shopee",
  "shop_id": 12345
}
```

## Architecture

```
Controller → Service → AuthService → SignatureUtil
                ↓            ↓
           RestTemplate  TokenCacheManager
                ↓
          Shopee API
```

## Key Features

- **Multi-shop support** — Tokens cached per shop ID
- **Thread-safe** — ConcurrentHashMap for token cache
- **Auto-retry** — 401 errors trigger token refresh + retry
- **Extensible webhooks** — Add new events without changing controller
