package com.base.base.aggregate;

import com.base.base.Aggregate;
import com.base.base.Command;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class DomainAggregate<A extends DomainAggregate<A, C>, C extends Command> implements Aggregate {
    protected final transient Logger logger = LogManager.getLogger(this.getClass());

    @Id
    @Column
    private String id;
    @Column(length = 100)
    private String version;
    private String createdBy;
    private Date createdDate;
    private Date updatedDate;
    private String lastModifiedBy;
}
