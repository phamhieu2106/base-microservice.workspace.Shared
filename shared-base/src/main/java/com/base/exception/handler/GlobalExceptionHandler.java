package com.base.exception.handler;

import com.base.BaseObjectLoggAble;
import com.base.domain.response.WrapResponse;
import com.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class GlobalExceptionHandler extends BaseObjectLoggAble {

    private final MessageSource messageSource;

    @Value("${spring.messages.basename:vi}")
    private String MESSAGES_BASENAME;

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
        logger.error("HttpMessageNotReadableException", ex);
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
        logger.error("PSQLException", ex);
        return WrapResponse.error(ex.getLocalizedMessage());
    }

    private String getMessage(String errorCode) {
        try {
            return messageSource.getMessage(errorCode, null, Locale.forLanguageTag(MESSAGES_BASENAME));
        } catch (NoSuchMessageException ex) {
            return errorCode;
        }
    }
}
