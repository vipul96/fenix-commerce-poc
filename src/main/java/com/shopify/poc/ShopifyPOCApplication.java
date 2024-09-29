package com.shopify.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShopifyPOCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopifyPOCApplication.class, args);
    }

}
