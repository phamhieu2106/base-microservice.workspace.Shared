package com.henry.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

@Configuration
public class ElasticsearchConfig {

    @Bean
    protected RestClient restClient() {
        return RestClient
                .builder(new HttpHost("localhost", 9991, "http"))
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
