package com.shopify.poc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.poc.dtos.FulfillmentDTO;
import com.shopify.poc.entity.Fulfillment;
import com.shopify.poc.repository.FulfillmentRepository;
import com.shopify.poc.service.FulfillmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FulfillmentServiceImpl implements FulfillmentService {

    private final FulfillmentRepository fulfillmentRepository;

    @Autowired
    public FulfillmentServiceImpl(FulfillmentRepository fulfillmentRepository) {
        this.fulfillmentRepository = fulfillmentRepository;
    }

    @Override
    @Async
    public void saveFulfillmentDetails(FulfillmentDTO fulfillmentDTO, String shopUrl) {
        try {
            log.info("Saving fulfillment details for shop: {}", shopUrl);

            Fulfillment fulfillment = new ObjectMapper().convertValue(fulfillmentDTO, Fulfillment.class);
            fulfillment.setTenantId(shopUrl);

            fulfillmentRepository.save(fulfillment);
            log.info("Fulfillment details saved successfully for shop: {}", shopUrl);
        } catch (Exception e) {
            log.error("Error saving fulfillment details for shop: {}. Error: {}", shopUrl, e.getMessage(), e);
            throw new RuntimeException("Failed to save fulfillment details", e);
        }
    }
}
