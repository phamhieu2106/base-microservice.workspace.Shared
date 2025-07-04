package com.base.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String mapObjectToString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <O> O stringToMapObject(String string, Class<O> clazz) throws JsonProcessingException {
        return objectMapper.readValue(string, clazz);
    }
}
