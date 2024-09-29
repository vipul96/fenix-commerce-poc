package com.shopify.poc.repository;

import com.shopify.poc.entity.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends ElasticsearchRepository<Order, Long> {

    List<Order> findByTenantId(String tenantId);

    Order findById(long orderId);
}