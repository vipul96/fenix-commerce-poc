package com.shopify.poc.externalService;

import com.shopify.poc.dtos.OrderResponse;

public interface ShopifyService {
    OrderResponse fetchShopifyOrderAndFulfillmentDetails(String shopUrl);

    String buildShopifyUrl(String shopUrl);
}
