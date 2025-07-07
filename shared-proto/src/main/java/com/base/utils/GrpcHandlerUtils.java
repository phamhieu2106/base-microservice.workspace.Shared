package com.base.utils;

import com.base.exception.ServiceException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

public class GrpcHandlerUtils {

    public static <T> T callInternal(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    public static <T> void handleInternal(StreamObserver<T> responseObserver, Supplier<T> supplier) {
        try {
            T response = supplier.get();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(ex.getMessage())
                    .asRuntimeException());
        }
    }

}
