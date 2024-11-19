package com.henry.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.henry.base.domain.request.BaseSort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElasticsearchUtils {

    public static NativeQueryBuilder getQueryBuildersSort(NativeQueryBuilder queryBuilder, List<BaseSort> sorts) {
        if (CollectionUtils.isEmpty(sorts)) return queryBuilder;
        sorts.forEach(sort -> queryBuilder.withSort(Sort.by(sort.isDecreasing() ? Sort.Direction.DESC : Sort.Direction.ASC, sort.getField())));
        return queryBuilder;
    }

    public static Query buildQueryBuilderWithFields(List<String> fields, String value) {
        if (StringUtils.isBlank(value) || CollectionUtils.isEmpty(fields)) return Query.of(q -> q.bool(b -> b));

        BoolQuery boolQuery = BoolQuery.of(b -> {
            fields.forEach(field -> b.should(s -> s.match(m -> m.field(field).query(value))));
            return b;
        });

        return Query.of(q -> q.bool(boolQuery));
    }

    public static Query buildFilterQueryWithValues(String field, List<String> values) {
        if (CollectionUtils.isEmpty(values)) return null;

        List<Query> termQueries = values.stream()
                .map(value -> Query.of(q -> q.term(t -> t.field(field).value(value))))
                .collect(Collectors.toList());

        return Query.of(q -> q.bool(b -> b.must(termQueries)));
    }

    public static Query buildFilterQueryWithValue(String field, String value) {
        return Query.of(q -> q.bool(b -> b.must(s -> s.term(m -> m.field(field).value(value)))));
    }

    public static Query combineFilters(List<Query> filters) {
        if (CollectionUtils.isEmpty(filters)) return null;

        return Query.of(q -> q.bool(b -> {
            filters.forEach(b::filter);
            return b;
        }));
    }

}
