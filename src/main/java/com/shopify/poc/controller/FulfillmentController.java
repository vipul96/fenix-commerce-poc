package com.shopify.poc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.poc.dtos.FulfillmentDTO;
import com.shopify.poc.service.FulfillmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/fulfillments")
@Slf4j
public class FulfillmentController {


    private final FulfillmentService fulfillmentService;

    @Autowired
    public FulfillmentController(FulfillmentService fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    @PostMapping("/webhooks")
    public ResponseEntity<Object> fulfillmentWebhook(@RequestBody String payload, @RequestHeader Map<String, Object> map) {
        try {
            String shopDomain = (String) map.get("x-shopify-shop-domain");
            FulfillmentDTO fulfillmentDTO = new ObjectMapper().readValue(payload, FulfillmentDTO.class);
            fulfillmentService.saveFulfillmentDetails(fulfillmentDTO, shopDomain);
            log.info("Fulfillment details saved successfully for shop: {}", shopDomain);
        } catch (Exception exception) {
            log.error("Error processing fulfillment webhook: {}", exception.getMessage(), exception);
            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }
}
