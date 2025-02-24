package com.henry.base.aggregate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.henry.base.BaseObjectLoggAble;
import com.henry.base.Command;
import com.henry.base.Event;
import com.henry.base.exception.ServiceException;
import com.henry.base.repository.EventRepository;
import com.henry.event.EventEntity;
import com.henry.util.GenerateUtils;
import com.henry.utils.ObjectMapperUtils;
import com.henry.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
@Transactional
public class BaseAggregate<A extends DomainAggregate<A, C>, C extends Command, R extends JpaRepository<A, String>> extends BaseObjectLoggAble {

    private final R repository;
    private final EventRepository eventRepository;
    private final Class<A> aggregateClass;

    public A save(C command) {
        try {
            A aggregate = aggregateClass.getDeclaredConstructor().newInstance();
            aggregate.setId(GenerateUtils.generateUUID());

            return handleEvent(command, aggregate);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException("EVENT_SOURCING.SAVE_AGGREGATE", e);
        }
    }

    public A update(String entityId, C command) {
        try {
            A aggregate = repository.findById(entityId)
                    .orElseThrow(() -> new ServiceException("EVENT_SOURCING.UPDATE_AGGREGATE", entityId));

            return handleEvent(command, aggregate);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException("EVENT_SOURCING.NOT_FOUND_AGGREGATE ", entityId);
        }
    }

    private A handleEvent(C command, A aggregate) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JsonProcessingException {
        Event event = (Event) aggregateClass
                .getDeclaredMethod("process", command.getClass())
                .invoke(aggregate, command);
        aggregateClass
                .getDeclaredMethod("apply", event.getClass())
                .invoke(aggregate, event);
        EventEntity eventEntity = EventEntity
                .mapEventEntity(aggregate.getId(), ObjectMapperUtils.mapObjectToString(event), event.getClass().getSimpleName(),
                        String.valueOf(ReflectionUtils.getFieldValue(command, command.getClass(), "actionUser")));
        aggregate.setVersion(eventRepository.save(eventEntity).getId());
        return repository.save(aggregate);
    }

}
