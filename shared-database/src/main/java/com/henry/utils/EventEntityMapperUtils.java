package com.henry.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.event.EventEntity;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class EventEntityMapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static EventEntity mapDataToEventEntity(String json) throws IOException {
        JsonNode root = objectMapper.readTree(json);

        JsonNode afterNode = root.path("after");
        if (afterNode.isMissingNode()) {
            throw new IllegalArgumentException("Missing 'after' data in JSON.");
        }

        String id = afterNode.path("id").asText();
        String entityId = afterNode.path("entity_id").asText();
        String eventType = afterNode.path("event_type").asText();
        String createdBy = afterNode.path("created_by").asText(null);

        long createdAtMicroseconds = afterNode.path("created_at").asLong();
        Date createdAt = new Date(createdAtMicroseconds / 1000);

        String entityDataStr = afterNode.path("entity_data").asText();
        Map<String, Object> entityData = objectMapper.readValue(entityDataStr, Map.class);

        return EventEntity.builder()
                .id(id)
                .entityId(entityId)
                .entityData(entityData)
                .eventType(eventType)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }
}
