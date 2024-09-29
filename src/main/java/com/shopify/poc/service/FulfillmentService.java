package com.shopify.poc.service;

import com.shopify.poc.dtos.FulfillmentDTO;

public interface FulfillmentService {
    void saveFulfillmentDetails(FulfillmentDTO fulfillmentDTO, String shopUrl);
}
