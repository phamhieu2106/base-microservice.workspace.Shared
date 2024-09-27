package com.henry.entity;

import com.henry.base.domain.BaseEntity;
import com.henry.constant.CustomJDBCType;
import com.henry.converter.JDBCConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "event")
@Getter
@Setter
public class EventEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventId;
    private String entityId;
    private String eventType;
    @Convert(converter = JDBCConverter.class)
    @Column(columnDefinition = CustomJDBCType.JSON)
    private Object entityData;
    private Date createdAt;
}
