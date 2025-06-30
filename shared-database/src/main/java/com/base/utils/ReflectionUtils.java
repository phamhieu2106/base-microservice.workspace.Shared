package com.base.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ReflectionUtils {

    @SneakyThrows
    public static Object getFieldValue(Object object, Class<?> clazz, String fieldName) {
        if (Objects.isNull(object)) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        Field field = Arrays.stream(fields)
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
        if (ObjectUtils.isNotEmpty(field)) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }


    public static List<String> getAllFieldNames(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return Collections.emptyList();
        }
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(Field::getName)
                .toList();
    }
}
