package com.base.base.workflow;

import com.base.base.BaseObjectLoggAble;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BaseWorkFlow extends BaseObjectLoggAble {
    protected ApplicationContext applicationContext;
}
