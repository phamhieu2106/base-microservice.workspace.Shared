package com.henry.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.ObjectUtils;

@Converter
public class JDBCConverterToJson implements AttributeConverter<Object, Object> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting object to JSON", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(Object dbData) {
        try {
            if (ObjectUtils.isEmpty(dbData)) return null;
            return objectMapper.readValue(dbData.toString(), Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to object", e);
        }
    }
}
