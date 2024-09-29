package com.shopify.poc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.poc.dtos.OrderDTO;
import com.shopify.poc.entity.Order;
import com.shopify.poc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    String secret = "61fb68399f2862709562dd4e840cd8fe";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/webhooks")
    public ResponseEntity<Object> orderWebhook(@RequestBody String payload, @RequestHeader Map<String, Object> map) {
        try {
            OrderDTO orderDTO = new ObjectMapper().readValue(payload, OrderDTO.class);
            String shopDomain = (String) map.get("x-shopify-shop-domain");
            orderService.saveOrderDetails(orderDTO, shopDomain);
            log.info("Order details saved successfully for shop domain: {}", shopDomain);
        } catch (Exception exception) {
            log.error("Error processing order webhook: {}", exception.getMessage(), exception);
            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<Iterable<Order>> getOrdersByTenant(@PathVariable String tenantId) {
        try {
            Iterable<Order> orders = orderService.getOrdersByTenant(tenantId);
            log.info("Retrieved orders for tenantId: {}", tenantId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Error retrieving orders for tenantId {}: {}", tenantId, exception.getMessage(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable long orderId) {
        try {
            Order order = orderService.getOrderByOrderId(orderId);
            if (!ObjectUtils.isEmpty(order)) {
                log.info("Retrieved order for orderId: {}", orderId);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                log.warn("Order not found for orderId: {}", orderId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            log.error("Error retrieving order for orderId {}: {}", orderId, exception.getMessage(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
