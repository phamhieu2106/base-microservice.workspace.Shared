package com.base.utils;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

public class GrpcHandlerUtils {

    public static <T> void handle(StreamObserver<T> responseObserver, Supplier<T> supplier) {
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
