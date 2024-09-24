package com.henry.base.controller;

import com.henry.base.BaseObjectLoggAble;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Getter
public class BaseController extends BaseObjectLoggAble {
    protected ApplicationContext applicationContext;
    protected ExecutorService executorService;
}
