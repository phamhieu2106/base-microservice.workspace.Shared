package com.henry.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories()
@Configuration
public class ElasticsearchConfig {
    @Value("${henry.elasticsearch.host}")
    private String HOST;
    @Value("${henry.elasticsearch.port}")
    private Integer PORT;

    @Bean
    protected RestClient restClient() {
        return RestClient
                .builder(new HttpHost(HOST, PORT, null))
                .build();
    }

    @Bean
    protected ElasticsearchTransport elasticsearchTransport() {
        return new RestClientTransport(
                restClient(), new JacksonJsonpMapper());
    }

    @Bean
    protected ElasticsearchClient elasticsearchClient() {
        return new ElasticsearchClient(elasticsearchTransport());
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(ElasticsearchClient elasticsearchClient) {
        return new ElasticsearchTemplate(elasticsearchClient);
    }
}
