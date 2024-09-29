package com.shopify.poc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.poc.dtos.OrderDTO;
import com.shopify.poc.entity.Order;
import com.shopify.poc.repository.OrderRepository;
import com.shopify.poc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Async
    public void saveOrderDetails(OrderDTO orderDTO, String shopUrl) {

        try {
            Order order = new ObjectMapper().convertValue(orderDTO, Order.class);
            order.setTenantId(shopUrl);

            orderRepository.save(order);
            log.info("Order details saved successfully for shop: {}", shopUrl);
        } catch (Exception e) {
            log.error("Error saving order details for shop: {}. Error: {}", shopUrl, e.getMessage(), e);
            throw new RuntimeException("Failed to save order details", e);
        }
    }

    @Override
    public Iterable<Order> getOrdersByTenant(String tenantId) {
        return orderRepository.findByTenantId(tenantId);
    }

    @Override
    public Order getOrderByOrderId(long orderId) {
        return orderRepository.findById(orderId);
    }
}
