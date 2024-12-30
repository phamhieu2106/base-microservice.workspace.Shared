package com.henry.base.utils;

import com.henry.base.BaseObjectLoggAble;
import com.henry.base.constant.HistoryType;
import com.henry.base.domain.BaseModel;
import com.henry.base.repository.HistoryRepository;
import com.henry.domain.entity.HistoryEntity;
import com.henry.domain.model.ChangeModel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoryUtils extends BaseObjectLoggAble {

    private final HistoryRepository historyRepository;

    public void saveHistory(String entityId, String entityCode, Class<?> clazz, Integer historyType, String content) {
        HistoryEntity historyEntity = HistoryEntity.builder()
                .entityId(entityId)
                .entityCode(entityCode)
                .entityName(clazz.getSimpleName())
                .type(historyType)
                .content(content)
                .createdDate(new Date())
                .build();
        historyRepository.save(historyEntity);
    }


    public void saveHistoryWithChanges(String entityId, String entityCode, Class<?> changeClazz, Class<?> clazz, Integer historyType, String content,
                                       Object changeObject, Object currentObject) {
        List<String> objectFieldNames = getAllFieldNames(changeClazz);
        List<ChangeModel> _changes = new ArrayList<>();

        handleGetChanges(_changes, objectFieldNames, changeObject, currentObject, changeClazz, clazz, null);

        HistoryEntity historyEntity = HistoryEntity.builder()
                .entityId(entityId)
                .entityCode(entityCode)
                .entityName(clazz.getSimpleName())
                .type(historyType)
                .content(content)
                .createdDate(new Date())
                ._changes(_changes)
                .build();
        historyRepository.save(historyEntity);
    }

    private void handleGetChanges(List<ChangeModel> _changes, List<String> objectFieldNames, Object changeObject, Object currentObject,
                                  Class<?> changeClazz, Class<?> clazz, String modelName) {
        if (CollectionUtils.isNotEmpty(objectFieldNames)
                && (ObjectUtils.isNotEmpty(changeObject) || ObjectUtils.isNotEmpty(currentObject))) {
            objectFieldNames.forEach(fieldName -> {
                Object prevData = getFieldValue(currentObject, clazz, fieldName);
                Object currentData = getFieldValue(changeObject, changeClazz, fieldName);
                if ((ObjectUtils.isEmpty(prevData) && ObjectUtils.isEmpty(currentData)) ||
                        Objects.equals(prevData, currentData)) {
                    return;
                }
                if (currentData instanceof BaseModel) {
                    handleAddChangeForModel(_changes, fieldName, currentData.getClass(), prevData, currentData);
                } else {
                    handleAddChange(_changes, modelName, fieldName, prevData, currentData);
                }
            });
        }
    }

    private void handleAddChangeForModel(List<ChangeModel> _changes, String modelName, Class<?> clazz,
                                         Object changeObject, Object currentObject) {
        List<String> fieldNames = getAllFieldNames(clazz);
        handleGetChanges(_changes, fieldNames, changeObject, currentObject, changeObject.getClass(), currentObject.getClass(), modelName);
    }

    private void handleAddChange(List<ChangeModel> _changes, String modelName, String fieldName, Object prevData, Object currentData) {
        ChangeModel change = new ChangeModel();
        change.setModelName(modelName);
        change.setFieldName(fieldName);
        change.setPrevData(prevData);
        change.setCurrentData(currentData);
        change.setAction(ObjectUtils.isEmpty(change.getPrevData())
                ? HistoryType.historyTypeMap.get(HistoryType.ADD)
                : HistoryType.historyTypeMap.get(HistoryType.UPDATE));
        _changes.add(change);
    }

    private Object getFieldValue(Object object, Class<?> clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        Field field = Arrays.stream(fields).toList()
                .stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
        if (ObjectUtils.isNotEmpty(field)) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                logger.error(">>> IllegalAccessException: getFieldValue HistoryUtils: not found value for field: {}", fieldName);
            }
        }
        return null;
    }

    private List<String> getAllFieldNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).toList()
                .stream()
                .map(Field::getName)
                .toList();
    }
}
