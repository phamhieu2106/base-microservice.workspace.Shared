package com.henry.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ElasticsearchRepositoryUtil {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public <O, R> Page<R> searchPage(Query query, Class<O> clazz, Class<R> clazzResponse) {
        SearchHits<O> searchHits = elasticsearchTemplate.search(query, clazz);
        List<R> content = MappingUtils.mapList(searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList(), clazzResponse);
        return new PageImpl<>(content, query.getPageable(), searchHits.getTotalHits());
    }
}
