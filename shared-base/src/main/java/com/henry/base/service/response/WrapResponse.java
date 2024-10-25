package com.henry.base.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class WrapResponse<D> {
    private D data;
    private Object message;
    private boolean success;
    private Integer statusCode;

    public static <T> WrapResponse<T> error(Object msg) {
        return WrapResponse.<T>builder()
                .data(null)
                .message(msg)
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    public static <T> WrapResponse<T> ok(T data) {
        return WrapResponse.<T>builder()
                .data(data)
                .message(null)
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
