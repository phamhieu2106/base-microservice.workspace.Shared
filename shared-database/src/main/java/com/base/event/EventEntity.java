package com.base.event;

import com.base.constant.JDBCCustomType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "events", indexes = {
        @Index(name = "entityId_eventType_createdAt_idx", columnList = "entityId, eventType, createdAt")
})
@Entity
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String entityId;
    @Column(columnDefinition = JDBCCustomType.JSON)
    @JdbcTypeCode(value = SqlTypes.JSON)
    private Object entityData;
    private String eventType;
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
        event.setCreatedBy(actionUser);
        return event;
    }
}
