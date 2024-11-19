package com.henry.base.workflow;

import com.henry.base.BaseObjectLoggAble;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Getter
public class BaseWorkFlow extends BaseObjectLoggAble {
    @Autowired
    protected ApplicationContext applicationContext;
}
