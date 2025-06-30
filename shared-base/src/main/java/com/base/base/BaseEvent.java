package com.base.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseEvent implements Serializable {
    protected String actionUser = "guest";
    private Date createdEventTime = new Date();
}
