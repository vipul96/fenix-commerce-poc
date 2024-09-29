package com.shopify.poc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.shopify.poc.dtos.Money;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Document(indexName = "orders")
public class Order {
    @Id
    private long id;
    private long appId;
    private String tenantId;
    private boolean buyerAcceptsMarketing;
    private String cancelReason;
    private String cancelledAt;
    private String cartToken;
    private String checkoutId;
    private String checkoutToken;
    private String clientDetails;
    private String closedAt;
    private String confirmationNumber;
    private boolean confirmed;
    private String createdAt;
    private String currency;
    private String currentSubtotalPrice;
    private Money currentSubtotalPriceSet;
    private String currentTotalAdditionalFeesSet;
    private String currentTotalDiscounts;
    private Money currentTotalDiscountsSet;
    private String currentTotalDutiesSet;
    private String currentTotalPrice;
    private Money currentTotalPriceSet;
    private String currentTotalTax;
    private Money currentTotalTaxSet;
    private String customerLocale;
    private String deviceId;
    private List<String> discountCodes;
    private boolean estimatedTaxes;
    private String financialStatus;
    private String fulfillmentStatus;
    private String landingSite;
    private String landingSiteRef;
    private String locationId;
    private String merchantOfRecordAppId;
    private String name;
    private String note;
    private List<String> noteAttributes;
    private int number;
    private int orderNumber;
    private String originalTotalAdditionalFeesSet;
    private String originalTotalDutiesSet;
    private List<String> paymentGatewayNames;
    private String poNumber;
    private String presentmentCurrency;
    private String processedAt;
    private String reference;
    private String referringSite;
    private String sourceIdentifier;
    private String sourceName;
    private String sourceUrl;
    private String subtotalPrice;
    private Money subtotalPriceSet;
    private String tags;
    private boolean taxExempt;
    private boolean taxesIncluded;
    private boolean test;
    private String token;
    private String totalDiscounts;
    private Money totalDiscountsSet;
    private String totalLineItemsPrice;
    private Money totalLineItemsPriceSet;
    private String totalOutstanding;
    private String totalPrice;
    private Money totalPriceSet;
    private Money totalShippingPriceSet;
    private String totalTax;
    private Money totalTaxSet;
    private String totalTipReceived;
    private int totalWeight;
    private String updatedAt;
    private String userId;
    private Object billingAddress;
    private Object customer;
}
