package com.henry.domain.entity;

import com.henry.constant.JDBCCustomType;
import com.henry.domain.model.ChangeModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    protected String entityId;
    protected String entityCode;
    protected String entityName;
    protected Integer type;
    protected String typeName;
    protected String content;
    protected Date createdDate;
    protected String createdBy;
    @Column(columnDefinition = JDBCCustomType.JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    protected List<ChangeModel> _changes;
}
