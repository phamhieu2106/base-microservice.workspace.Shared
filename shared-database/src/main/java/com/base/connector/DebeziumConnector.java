package com.base.connector;

import com.base.BaseObjectLoggAble;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class DebeziumConnector extends BaseObjectLoggAble {

    @Value("${henry.connector.url:}")
    private String CONNECTION_URL;

    private final RestTemplate restTemplate;

    public DebeziumConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendConnector(String connectorName, String json) {
        String url = CONNECTION_URL;
        String connectorUrl = url + "/" + connectorName;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(connectorUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Connector '{}' already exists. Skipping creation.", connectorName);
                return;
            }
        } catch (HttpClientErrorException.NotFound ignored) {
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        try {
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            logger.info("Debezium connector created successfully: {}", response);
        } catch (HttpClientErrorException.Conflict e) {
            logger.warn("Connector '{}' already exists. Skipping creation.", connectorName);
        }
    }
}
