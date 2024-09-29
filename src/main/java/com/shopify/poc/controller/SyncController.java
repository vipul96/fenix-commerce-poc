package com.shopify.poc.controller;

import com.shopify.poc.service.SyncDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncDataService syncDataService;

    public SyncController(SyncDataService syncDataService) {
        this.syncDataService = syncDataService;
    }

    @GetMapping("/orders-fulfillments")
    public void syncHistoricalOrderAndFulfillment(@RequestParam String shopUrl) {
        syncDataService.syncShopifyOrderAndFulfillmentData(shopUrl);
    }
}
