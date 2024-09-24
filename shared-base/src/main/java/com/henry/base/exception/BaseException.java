package com.henry.base.exception;

import com.henry.util.MessageSourceUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{

    protected String errorCode;
    protected String message;

    protected BaseException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.message = MessageSourceUtils.getMessage(errorCode, args);
    }
}
