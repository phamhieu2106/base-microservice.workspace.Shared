package com.henry.base.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WrapResponse<D> {
    private D data;
    private String message;
    private boolean success;

    public static <T> WrapResponse<T> error(String msg) {
        return WrapResponse.<T>builder()
                .data(null)
                .message(msg)
                .success(false)
                .build();
    }

    public static <T> WrapResponse<T> ok(T data) {
        return WrapResponse.<T>builder()
                .data(data)
                .message(null)
                .success(true)
                .build();
    }
}
