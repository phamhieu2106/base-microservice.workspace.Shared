package com.henry.base.command_processor;

import java.lang.reflect.Method;

public class CommandProcessor<A, C> {

    protected Object invokeProcessCommand(A aggregate, C command) {
        try {
            // Lấy phương thức có tên 'processCommand' và có tham số là kiểu của command
            Method method = aggregate.getClass().getMethod("processCommand", command.getClass());
            return method.invoke(aggregate, command);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke processCommand", e);
        }
    }
}
