package com.inventrik.integration.shopee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Provides a pre-configured {@link RestTemplate} bean for all Shopee API
 * communication.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(ShopeeConfig.CONNECT_TIMEOUT_MS);
        factory.setReadTimeout(ShopeeConfig.READ_TIMEOUT_MS);
        return new RestTemplate(factory);
    }
}
