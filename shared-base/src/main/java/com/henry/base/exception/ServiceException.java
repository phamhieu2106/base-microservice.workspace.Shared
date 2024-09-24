package com.henry.base.exception;

public class ServiceException extends BaseException{
    public ServiceException(String errorCode, Object... args) {
        super(errorCode, args);
    }
}
