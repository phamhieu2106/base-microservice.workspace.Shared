package com.henry.base;

import com.henry.Aggregate;
import com.henry.Command;
import com.henry.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class DomainAggregate<E extends Event, C extends Command> implements Aggregate {
    protected final transient Logger logger = LogManager.getLogger(this.getClass());

    @Id
    @Column
    private String id;
    @Column(length = 100)
    private String version;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date updatedDate;
    @LastModifiedBy
    private String lastModifiedBy;

    protected abstract Class<?> loadRepositoryClazz();
}
