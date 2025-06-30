package com.base.base.controller;

import com.base.base.BaseObjectLoggAble;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;

@Getter
public class BaseController extends BaseObjectLoggAble {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected ExecutorService executorService;
}
