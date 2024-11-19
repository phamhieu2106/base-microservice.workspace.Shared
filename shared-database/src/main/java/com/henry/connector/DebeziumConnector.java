package com.henry.connector;

import com.henry.base.BaseObjectLoggAble;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class DebeziumConnector extends BaseObjectLoggAble {

    private final RestTemplate restTemplate;

    public DebeziumConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sendConnector(String connectorName, String configURL) {
        String url = "http://localhost:8083/connectors";
        String connectorUrl = url + "/" + connectorName;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(connectorUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                getLogger().info("Connector '{}' already exists. Skipping creation.", connectorName);
                return;
            }
        } catch (HttpClientErrorException.NotFound e) {
            // Connector does not exist, continue to create it
        }

        String json;
        try {
            ClassPathResource resource = new ClassPathResource(configURL);
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            json = new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            getLogger().error("Failed to load Debezium connector configuration", e);
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        try {
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            getLogger().info("Debezium connector created successfully: {}", response);
        } catch (HttpClientErrorException.Conflict e) {
            getLogger().warn("Connector '{}' already exists. Skipping creation.", connectorName);
        }
    }
}
