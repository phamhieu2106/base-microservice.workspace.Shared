package com.base.config;

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

    @Value("${spring.elasticsearch.uris:localhost:9200}")
    private String ELASTICSEARCH_HOST;

    @Bean
    protected ElasticsearchTransport elasticsearchTransport() {
        String[] hosts = ELASTICSEARCH_HOST.split(":");
        return new RestClientTransport(RestClient.builder(new HttpHost(hosts[0], Integer.parseInt(hosts[1])))
                .build(), new JacksonJsonpMapper());
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
