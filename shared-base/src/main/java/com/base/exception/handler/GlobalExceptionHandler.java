package com.base.exception.handler;

import com.base.BaseObjectLoggAble;
import com.base.domain.response.WrapResponse;
import com.base.exception.ServiceException;
import com.base.util.MessageSourceUtils;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends BaseObjectLoggAble {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public WrapResponse<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", error.getField());
            errorDetails.put("reason", MessageSourceUtils.getMessage(error.getDefaultMessage()));
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
        logger.error(ex.getLocalizedMessage(), ex);
        return WrapResponse.error(MessageSourceUtils.getMessage(ex.getErrorCode()));
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public WrapResponse<List<String>> handlePSQLException(PSQLException ex) {
        logger.error("PSQLException", ex);
        return WrapResponse.error(ex.getLocalizedMessage());
    }

}
