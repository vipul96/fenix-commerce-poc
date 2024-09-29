package com.shopify.poc.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "shopify")
public class ShopifyProperties {

    private String baseUrl;
    private String apiVersion;
    private String orderUrl;
    private String accessToken;
}
