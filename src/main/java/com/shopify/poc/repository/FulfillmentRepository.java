package com.shopify.poc.repository;

import com.shopify.poc.entity.Fulfillment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FulfillmentRepository extends ElasticsearchRepository<Fulfillment, Long> {
}
