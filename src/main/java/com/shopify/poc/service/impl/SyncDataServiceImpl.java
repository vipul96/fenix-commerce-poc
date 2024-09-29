package com.shopify.poc.service.impl;

import com.shopify.poc.dtos.FulfillmentDTO;
import com.shopify.poc.dtos.OrderDTO;
import com.shopify.poc.dtos.OrderResponse;
import com.shopify.poc.externalService.ShopifyService;
import com.shopify.poc.service.FulfillmentService;
import com.shopify.poc.service.OrderService;
import com.shopify.poc.service.SyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
@Slf4j
public class SyncDataServiceImpl implements SyncDataService {

    private final ShopifyService shopifyService;

    private final OrderService orderService;

    private final FulfillmentService fulfillmentService;

    @Autowired
    public SyncDataServiceImpl(ShopifyService shopifyService, OrderService orderService, FulfillmentService fulfillmentService) {

        this.shopifyService = shopifyService;
        this.orderService = orderService;
        this.fulfillmentService = fulfillmentService;
    }

    @Override
    public void syncShopifyOrderAndFulfillmentData(String shopUrl) {
        boolean nextPage = true;
        String nextPageUrl = shopifyService.buildShopifyUrl(shopUrl);

        while (nextPage) {
            try {
                OrderResponse orderResponse = shopifyService.fetchShopifyOrderAndFulfillmentDetails(nextPageUrl);
                if (!ObjectUtils.isEmpty(orderResponse) && !ObjectUtils.isEmpty(orderResponse.getOrders())) {
                    log.info("Fetched {} orders from Shopify.", orderResponse.getOrders().size());
                    saveOrderAndFulfillmentDetails(orderResponse.getOrders(), shopUrl);
                    if (!ObjectUtils.isEmpty(orderResponse.getNextPageUrl())) {
                        nextPageUrl = orderResponse.getNextPageUrl();
                        log.info("Moving to next page: {}", nextPageUrl);
                    } else {
                        nextPage = false;
                        log.info("No more pages to fetch.");
                    }
                } else {
                    log.warn("No orders found in response.");
                    nextPage = false;
                }
            } catch (Exception e) {
                log.error("Error while fetching orders for shop {}: {}", shopUrl, e.getMessage(), e);
                nextPage = false; // Stop fetching on error
            }
        }
    }

    private void saveOrderAndFulfillmentDetails(List<OrderDTO> orderDetails, String shopUrl) {
        for (OrderDTO orderDTO : orderDetails) {
            try {
                // Save Order details
                log.info("Saving order details for order ID: {}", orderDTO.getId());
                orderService.saveOrderDetails(orderDTO, shopUrl);

                // Save fulfillment details
                if (!ObjectUtils.isEmpty(orderDTO.getFulfillments())) {
                    for (FulfillmentDTO fulfillmentDTO : orderDTO.getFulfillments()) {
                        log.info("Saving fulfillment details for order ID: {} and fulfillment ID: {}", orderDTO.getId(), fulfillmentDTO.getId());
                        fulfillmentService.saveFulfillmentDetails(fulfillmentDTO, shopUrl);
                    }
                }
            } catch (Exception e) {
                log.error("Error saving details for order ID {}: {}", orderDTO.getId(), e.getMessage(), e);
            }
        }
    }
}
