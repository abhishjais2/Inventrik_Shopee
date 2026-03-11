package com.inventrik.shopee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstrap class for the Shopee Integration Library.
 * <p>
 * This module is a reusable communication SDK — it contains no business logic.
 * When integrated into a larger system, this class can be removed and the
 * components will be picked up via component scanning.
 */
@SpringBootApplication
public class ShopeeIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopeeIntegrationApplication.class, args);
    }
}
