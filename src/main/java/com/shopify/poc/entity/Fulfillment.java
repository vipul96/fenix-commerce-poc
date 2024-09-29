package com.shopify.poc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Document(indexName = "fulfillments")
public class Fulfillment {
    @Id
    private long id;
    private String tenantId;
    private String createdAt;
    private long locationId;
    private String name;
    private long orderId;
    private Object originAddress; // Define properly if structure is known
    private Object receipt; // Define properly if structure is known
    private String service;
    private String shipmentStatus;
    private String status;
    private String trackingCompany;
    private String trackingNumber;
    private List<String> trackingNumbers;
    private String trackingUrl;
    private List<String> trackingUrls;
    private String updatedAt;
}
