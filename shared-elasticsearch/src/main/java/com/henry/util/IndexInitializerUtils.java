package com.henry.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesRequest;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import co.elastic.clients.elasticsearch.indices.GetMappingRequest;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import com.henry.base.BaseObjectLoggAble;
import com.henry.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.reindex.ReindexRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IndexInitializerUtils extends BaseObjectLoggAble {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchClient client;

    @Transactional(rollbackFor = Exception.class)
    public void recreateIndices(Class<?> viewClass) {
        if (!viewClass.isAnnotationPresent(Document.class)) {
            logger.error(">>> recreateIndices - Document.class not found: {}", viewClass.getSimpleName());
            return;
        }
        String documentName = viewClass.getAnnotation(Document.class).indexName();
        logger.debug(">>>>>> Start recreateIndices for Class: {}", viewClass);

        String oldIndexName = getCurrentIndexName(documentName);
        if (StringUtils.isBlank(oldIndexName)) {
            throw new ServiceException("Document name not found: " + oldIndexName);
        }
        IndexCoordinates oldIndexCoordinates = IndexCoordinates.of(oldIndexName);

        String newIndexName = documentName + "-" + UUID.randomUUID();
        IndexCoordinates indexCoordinates = IndexCoordinates.of(newIndexName);

        elasticsearchOperations.indexOps(indexCoordinates).create();
        elasticsearchOperations.indexOps(indexCoordinates).putMapping(viewClass);

        // Reindex từ index cũ sang index mới
        reindexData(oldIndexCoordinates, indexCoordinates);

        setAlias(documentName, oldIndexName, newIndexName);

        logger.debug(">>>>>> End recreateIndices for Class: {}", viewClass);
    }

    private void setAlias(String documentName, String oldIndexName, String newIndexName) {
        AliasActionParameters aliasActionParameters = AliasActionParameters.builder()
                .withIndices(newIndexName)  // Set the index name
                .withAliases(documentName)  // Set the alias name
                .build();
        AliasAction.Add aliasAction = new AliasAction.Add(aliasActionParameters);
        // Create an AliasActions instance and add the alias action to it
        AliasActions aliasActions = new AliasActions();
        aliasActions.add(aliasAction);
        // Apply the alias actions to the new index
        elasticsearchOperations.indexOps(IndexCoordinates.of(oldIndexName)).delete();
        elasticsearchOperations.indexOps(IndexCoordinates.of(newIndexName)).alias(aliasActions);
    }

    private String getCurrentIndexName(String documentName) {
        List<String> indices = getIndices(documentName);
        logger.debug(">>>>>> GetCurrentIndexName indices: {}", indices);
        if (indices.size() == 1) {
            return indices.get(0);
        }

        for (String index : indices.subList(1, indices.size())) {
            elasticsearchOperations.indexOps(IndexCoordinates.of(index)).delete();
        }

        return indices.get(0);
    }

    public List<String> getIndices(String documentName) {
        IndicesRequest request = IndicesRequest.of(builder -> builder.index(documentName + "*"));
        List<String> indices;
        try {
            indices = client.cat().indices(request).valueBody().stream()
                    .map(IndicesRecord::index)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return indices;
    }

    private void reindexData(IndexCoordinates sourceIndex, IndexCoordinates destIndex) {
        ReindexRequest reindexRequest = ReindexRequest.builder(sourceIndex, destIndex)
                .withScroll(Duration.ofMinutes(1)) // Đặt thời gian tồn tại của scroll context
                .withSlices(5L) // Chia công việc reindex thành 5 slice
                .withRequestsPerSecond(500L) // Giới hạn số request mỗi giây
                .build();
        elasticsearchOperations.reindex(reindexRequest);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMapping(String indexName) {
        GetMappingRequest request = GetMappingRequest.of(builder -> builder.index(indexName));
        try {
            GetMappingResponse response = client.indices().getMapping(request);
            return (Map<String, Object>) response.result().get(indexName).mappings().source();
        } catch (IOException e) {
            throw new RuntimeException("Error fetching mapping for index: " + indexName, e);
        }
    }
}
