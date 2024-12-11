package com.henry.domain.entity;

import com.henry.constant.CustomJDBCType;
import com.henry.domain.model.ChangeModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        indexes = {
                @Index(name = "entityId_idx", columnList = "entityId")
        })
@Builder
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String entityId;
    private String entityCode;
    private String entityName;
    private Integer type;
    private String content;
    private Date createdDate;
    @Column(columnDefinition = CustomJDBCType.JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ChangeModel> _changes;
}
