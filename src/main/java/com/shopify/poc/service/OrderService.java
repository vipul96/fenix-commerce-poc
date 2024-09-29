package com.shopify.poc.service;

import com.shopify.poc.dtos.OrderDTO;
import com.shopify.poc.entity.Order;

public interface OrderService {

    void saveOrderDetails(OrderDTO orderDTO, String shopUrl);

    Iterable<Order> getOrdersByTenant(String tenantId);

    Order getOrderByOrderId(long orderId);
}
