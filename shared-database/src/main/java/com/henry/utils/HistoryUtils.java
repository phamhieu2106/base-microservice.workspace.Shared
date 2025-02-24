package com.henry.utils;

import com.henry.base.BaseObjectLoggAble;
import com.henry.base.constant.HistoryType;
import com.henry.base.domain.BaseModel;
import com.henry.domain.entity.HistoryEntity;
import com.henry.domain.model.ChangeModel;
import com.henry.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class HistoryUtils<E extends HistoryEntity, R extends JpaRepository<E, String>> extends BaseObjectLoggAble {

    private final R repository;
    private final Class<E> historyClass;

    public void saveHistory(String entityId, String entityCode, Class<?> clazz, Integer historyType, String content) {
        try {
            E historyEntity = getHistoryEntity(entityId, entityCode, clazz, historyType, content);
            repository.save(historyEntity);
        } catch (Exception e) {
            logger.error("Failed to create history entity", e);
            throw new RuntimeException("Error creating history entity", e);
        }
    }

    public void saveHistoryWithChanges(String entityId, String entityCode, Class<?> changeClazz, Class<?> clazz,
                                       Integer historyType, String content, Object changeObject, Object currentObject) {
        List<String> objectFieldNames = ReflectionUtils.getAllFieldNames(changeClazz);
        List<ChangeModel> _changes = new ArrayList<>();

        handleGetChanges(_changes, objectFieldNames, changeObject, currentObject, changeClazz, clazz, null);

        try {
            E historyEntity = getHistoryEntity(entityId, entityCode, clazz, historyType, content);
            historyEntity.set_changes(_changes);
            repository.save(historyEntity);
        } catch (Exception e) {
            logger.error("Failed to create history entity", e);
            throw new RuntimeException("Error creating history entity", e);
        }
    }

    private E getHistoryEntity(String entityId, String entityCode, Class<?> clazz, Integer historyType, String content)
            throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        E historyEntity = historyClass.getDeclaredConstructor().newInstance();
        historyEntity.setEntityId(entityId);
        historyEntity.setEntityCode(entityCode);
        historyEntity.setEntityName(clazz.getSimpleName());
        historyEntity.setType(historyType);
        historyEntity.setTypeName(HistoryType.historyTypeMap.get(historyType));
        historyEntity.setContent(content);
        historyEntity.setCreatedDate(new Date());
        return historyEntity;
    }

    private void handleGetChanges(List<ChangeModel> _changes, List<String> objectFieldNames, Object changeObject, Object currentObject,
                                  Class<?> changeClazz, Class<?> clazz, String modelName) {
        if (CollectionUtils.isNotEmpty(objectFieldNames)
                && (ObjectUtils.isNotEmpty(changeObject) || ObjectUtils.isNotEmpty(currentObject))) {
            objectFieldNames.forEach(fieldName -> {
                Object prevData = ReflectionUtils.getFieldValue(currentObject, clazz, fieldName);
                Object currentData = ReflectionUtils.getFieldValue(changeObject, changeClazz, fieldName);
                if ((ObjectUtils.isEmpty(prevData) && ObjectUtils.isEmpty(currentData)) ||
                        Objects.equals(prevData, currentData)) {
                    return;
                } else if (prevData instanceof Date || currentData instanceof Date) {
                    prevData = MappingUtils.mapObject(prevData, Date.class).getTime();
                    currentData = MappingUtils.mapObject(currentData, Date.class).getTime();
                    if (Objects.equals(prevData, currentData)) return;
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
        List<String> fieldNames = ReflectionUtils.getAllFieldNames(clazz);
        handleGetChanges(_changes, fieldNames, currentObject, changeObject, Objects.isNull(changeObject) ? currentObject.getClass() : changeObject.getClass(),
                currentObject.getClass(), modelName);
    }

    private void handleAddChange(List<ChangeModel> _changes, String modelName, String fieldName, Object prevData, Object currentData) {
        ChangeModel change = new ChangeModel();
        change.setModelName(modelName);
        change.setFieldName(fieldName);
        change.setPrevData(prevData);
        change.setCurrentData(currentData);
        change.setAction(ObjectUtils.isEmpty(change.getPrevData()) ? HistoryType.historyTypeMap.get(HistoryType.ADD)
                : ObjectUtils.isEmpty(change.getCurrentData())
                ? HistoryType.historyTypeMap.get(HistoryType.DELETE)
                : HistoryType.historyTypeMap.get(HistoryType.UPDATE));
        _changes.add(change);
    }


}
