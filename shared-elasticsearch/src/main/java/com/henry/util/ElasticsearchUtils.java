package com.henry.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import com.henry.base.domain.request.BaseSort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticsearchUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static NativeQueryBuilder getQueryBuildersSort(NativeQueryBuilder queryBuilder, List<BaseSort> sorts) {
        if (CollectionUtils.isEmpty(sorts)) return queryBuilder;
        sorts.forEach(sort -> queryBuilder.withSort(Sort.by(sort.isDecreasing() ? Sort.Direction.DESC : Sort.Direction.ASC, sort.getField())));
        return queryBuilder;
    }

    public static Query buildQueryBuilderWithFields(List<String> fields, String value) {
        if (StringUtils.isBlank(value) || CollectionUtils.isEmpty(fields)) return Query.of(q -> q.bool(b -> b));

        BoolQuery boolQuery = BoolQuery.of(b -> b.should(s -> s.multiMatch(mm -> mm.fields(fields).query(value))));

        return Query.of(q -> q.bool(boolQuery));
    }

    public static Query buildFilterQueryWithValues(String field, List<String> values) {
        List<Query> termQueries = values.stream()
                .map(value -> Query.of(q -> q.term(t -> t.field(field).value(value))))
                .toList();

        return Query.of(q -> q.bool(b -> b.must(termQueries)));
    }

    public static Query buildFilterQueryWithValue(String field, String value) {
        return Query.of(q -> q.bool(b -> b.must(s -> s.term(m -> m.field(field).value(value)))));
    }

    public static Query buildMustNotFilterQueryWithValues(String field, List<String> values) {
        List<Query> mustNotQueries = values.stream()
                .map(value -> Query.of(q -> q.term(t -> t.field(field).value(value))))
                .toList();

        return Query.of(q -> q.bool(b -> b.mustNot(mustNotQueries)));
    }

    public static Query buildMustNotFilterQueryWithValue(String field, String value) {
        return Query.of(q -> q.bool(b -> b.mustNot(mn -> mn.term(t -> t.field(field).value(value)))));
    }

    public static Query buildFilterQueryWithRangeDate(String field, Date from, Date to) {
        return Query.of(q -> q.range(r -> {
            RangeQuery.Builder builder = r.field(field);
            if (ObjectUtils.isNotEmpty(from)) {
                builder.from(DATE_FORMAT.format(from));
            }
            if (ObjectUtils.isNotEmpty(to)) {
                builder.to(DATE_FORMAT.format(to));
            }
            return builder;
        }));
    }


    public static Query combineFilters(List<Query> filters) {
        if (CollectionUtils.isEmpty(filters)) return null;

        return Query.of(q -> q.bool(b -> {
            filters.forEach(b::filter);
            return b;
        }));
    }

}
