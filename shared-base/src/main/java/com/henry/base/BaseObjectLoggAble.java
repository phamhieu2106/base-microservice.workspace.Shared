package com.henry.base;

import java.util.logging.Logger;

public class BaseObjectLoggAble {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected  Logger getLogger() {return this.logger;}
}
