package com.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories()
@Configuration
public class ElasticsearchConfig {
//
//    @Value("${spring.elasticsearch.uris:http://localhost:9200}")
//    private String ELASTICSEARCH_HOST;
//
//    @Bean
//    protected ElasticsearchTransport elasticsearchTransport() {
//        String[] hosts = ELASTICSEARCH_HOST.split(":");
//        return new RestClientTransport(RestClient.builder(new HttpHost(hosts[0], Integer.parseInt(hosts[2])))
//                .build(), new JacksonJsonpMapper());
//    }
//
//    @Bean
//    protected ElasticsearchClient elasticsearchClient() {
//        return new ElasticsearchClient(elasticsearchTransport());
//    }
//
//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(ElasticsearchClient elasticsearchClient) {
//        return new ElasticsearchTemplate(elasticsearchClient);
//    }
}
