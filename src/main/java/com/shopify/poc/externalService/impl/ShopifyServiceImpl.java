package com.shopify.poc.externalService.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.poc.dtos.OrderDTO;
import com.shopify.poc.dtos.OrderResponse;
import com.shopify.poc.externalService.ShopifyService;
import com.shopify.poc.utils.APIUtils;
import com.shopify.poc.utils.ShopifyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ConfigurationProperties(prefix = "shopify")
@Slf4j
public class ShopifyServiceImpl implements ShopifyService {
    private final APIUtils apiUtils;
    private final ShopifyProperties shopifyProperties;

    @Autowired
    public ShopifyServiceImpl(APIUtils apiUtils, ShopifyProperties shopifyProperties) {
        this.apiUtils = apiUtils;
        this.shopifyProperties = shopifyProperties;
    }

    @Override
    public OrderResponse fetchShopifyOrderAndFulfillmentDetails(String shopUrl) {
        OrderResponse orderResponse = new OrderResponse();
        log.info("Fetching orders from Shopify for shop URL: {}", shopUrl);
        ResponseEntity<String> ordersResponse = apiUtils.get(shopUrl, constructShopifyHeaders());

        if (!ObjectUtils.isEmpty(ordersResponse) && ordersResponse.getStatusCode() == HttpStatus.OK) {
            log.info("Successfully fetched orders from Shopify for shop URL: {}", shopUrl);
            HashMap<String, Object> orderMap = new ObjectMapper().convertValue(ordersResponse.getBody(), HashMap.class);
            List<OrderDTO> orderDTOS = (List<OrderDTO>) orderMap.get("orders");
            orderResponse.setOrders(orderDTOS);

            // Process the fetched orders (orderDTOS) as needed

            // Check the Link header for pagination
            String linkHeader = ordersResponse.getHeaders().getFirst("Link");
            shopUrl = extractNextPageUrl(linkHeader); // Update finalUrl for the next iteration
            if (!ObjectUtils.isEmpty(shopUrl)) {
                orderResponse.setNextPageUrl(shopUrl);
                log.info("Next page URL for fetching more orders: {}", shopUrl);
            }

        } else {
            String errorMessage = ordersResponse != null
                    ? "Failed to fetch orders from Shopify: " + ordersResponse.getStatusCode()
                    : "Failed to fetch orders from Shopify: No response";

            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        log.info("Finished fetching orders for shop: {}", shopUrl);
        return orderResponse;
    }

    private Map<String, String> constructShopifyHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Shopify-Access-Token", shopifyProperties.getAccessToken());
        headers.put("Content-Type", "application/json");
        return headers;
    }

    // Helper method to extract the next page URL from the Link header
    private String extractNextPageUrl(String linkHeader) {
        String[] links = linkHeader.split(",");
        for (String link : links) {
            if (link.contains("rel=\"next\"")) {
                // Extract the URL part
                return link.substring(link.indexOf("<") + 1, link.indexOf(">"));
            }
        }
        return null;
    }

    @Override
    public String buildShopifyUrl(String shopUrl) {
        return UriComponentsBuilder.fromHttpUrl(String.format(shopifyProperties.getBaseUrl(), shopUrl, shopifyProperties.getApiVersion()) + shopifyProperties.getOrderUrl())
                .queryParam("status", "any")
                .queryParam("limit", 250)
                .toUriString();
    }
}
