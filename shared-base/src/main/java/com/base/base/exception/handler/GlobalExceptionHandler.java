package com.base.base.exception.handler;

import com.base.base.BaseObjectLoggAble;
import com.base.base.domain.response.WrapResponse;
import com.base.base.exception.ServiceException;
import org.postgresql.util.PSQLException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseObjectLoggAble {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WrapResponse<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", error.getField());
            errorDetails.put("reason", getMessage(error.getDefaultMessage()));
            errors.add(errorDetails);
        }
        return WrapResponse.error(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public WrapResponse<List<String>> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        return WrapResponse.error(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public WrapResponse<List<String>> handleServiceException(ServiceException ex) {
        return WrapResponse.error(getMessage(ex.getErrorCode()));
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public WrapResponse<List<String>> handlePSQLException(PSQLException ex) {
        return WrapResponse.error(ex.getLocalizedMessage());
    }

    private String getMessage(String errorCode) {
        try {
            return messageSource.getMessage(errorCode, null, Locale.forLanguageTag("")); //already locale in config
        } catch (NoSuchMessageException ex) {
            return errorCode;
        }
    }
}
