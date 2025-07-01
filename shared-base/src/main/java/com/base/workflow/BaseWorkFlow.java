package com.base.workflow;

import com.base.BaseObjectLoggAble;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BaseWorkFlow extends BaseObjectLoggAble {
    protected ApplicationContext applicationContext;
}
