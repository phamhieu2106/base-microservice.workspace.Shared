package com.henry.base.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    protected Date createdAt;
    protected String createdBy;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    protected Date updatedAt;
    protected String updatedBy;
}
