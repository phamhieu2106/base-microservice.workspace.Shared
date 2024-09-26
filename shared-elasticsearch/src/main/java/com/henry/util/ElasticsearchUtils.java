package com.henry.util;

import lombok.Setter;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
public class ElasticsearchUtils {

    private static ElasticsearchTemplate elasticsearchTemplate;

    public static <O> List<O> search(Query query, Class<O> clazz) {
        SearchHits<O> searchHits = elasticsearchTemplate.search(query, clazz);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
