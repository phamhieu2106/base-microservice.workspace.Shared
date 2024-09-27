package com.henry.base.exception.handler;

import com.henry.base.exception.ServiceException;
import com.henry.base.service.response.WrapResponse;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public WrapResponse<List<String>> handleServiceException(ServiceException ex) {
        return WrapResponse.error(getMessage(ex.getErrorCode()));
    }

    private String getMessage(String errorCode) {
        return messageSource.getMessage(errorCode, null, Locale.forLanguageTag("")); //already locale in config
    }
}
