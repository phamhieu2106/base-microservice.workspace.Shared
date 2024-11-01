package com.henry.event;

import com.henry.base.domain.BaseEntity;
import com.henry.converter.JDBCConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
@Builder
public class EventEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String entityId;
    @Convert(converter = JDBCConverter.class)
    private Object entityData;
    private String eventType;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    protected Date createdAt;
    protected String createdBy;

    public static EventEntity mapEventEntity(String entityId, Object entityData, String eventType, String actionUser) {
        Date now = new Date();
        EventEntity event = EventEntity.builder()
                .entityId(entityId)
                .entityData(entityData)
                .eventType(eventType)
                .build();
        event.setCreatedAt(now);
        event.setUpdatedAt(now);
        event.setCreatedBy(actionUser);
        return event;
    }
}
