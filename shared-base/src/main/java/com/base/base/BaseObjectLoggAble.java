package com.base.base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseObjectLoggAble {
    protected final transient Logger logger = LogManager.getLogger(this.getClass());

    protected Logger getLogger() {
        return this.logger;
    }
}
