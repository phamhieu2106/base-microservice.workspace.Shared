package com.henry.base.controller;

import com.henry.base.BaseObjectLoggAble;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class BaseController extends BaseObjectLoggAble {
    @Autowired
    protected ApplicationContext applicationContext;
    protected ExecutorService executorService = Executors.newCachedThreadPool();
}
