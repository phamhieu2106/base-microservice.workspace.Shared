package com.henry.base.aggregate;

import com.henry.Command;
import com.henry.base.BaseObjectLoggAble;
import com.henry.base.exception.ServiceException;
import com.henry.base.repository.EventRepository;
import com.henry.event.EventEntity;
import com.henry.util.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
            EventEntity event = aggregate.processCommand(command);
            aggregate.setVersion(eventRepository.save(event).getId());

            return repository.save(aggregate);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException("EVENT_SOURCING.SAVE_AGGREGATE", e);
        }
    }

    public A update(String entityId, C command) {
        try {
            A aggregate = repository.findById(entityId)
                    .orElseThrow(() -> new ServiceException("EVENT_SOURCING.UPDATE_AGGREGATE", entityId));
            EventEntity event = aggregate.processCommand(command);
            aggregate.setVersion(eventRepository.save(event).getId());

            return repository.save(aggregate);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new ServiceException("EVENT_SOURCING.NOT_FOUND_AGGREGATE ", entityId);
        }
    }

}
