package com.shopify.poc.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class APIUtils {

    private final RestTemplate restTemplate;

    public APIUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private <T> ResponseEntity<T> request(HttpMethod method, String url, Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(url, method, entity, (Class<T>) Object.class);
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> headers) {
        return request(HttpMethod.GET, url, null, headers);
    }

    public <T> ResponseEntity<T> post(String url, Object body, Map<String, String> headers) {
        return request(HttpMethod.POST, url, body, headers);
    }

    public <T> ResponseEntity<T> put(String url, Object body, Map<String, String> headers) {
        return request(HttpMethod.PUT, url, body, headers);
    }

    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers) {
        return request(HttpMethod.DELETE, url, null, headers);
    }
}