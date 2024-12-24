package com.henry.base.func;

import com.henry.base.BaseObjectLoggAble;
import com.henry.service.LockService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.Supplier;

@Component
@NoArgsConstructor
public class BaseFunc extends BaseObjectLoggAble implements Serializable {

    @Autowired
    private LockService lockService;

    protected <T> void execInLock(String keyLock, Supplier<T> supplier) {
        lockService.lock(keyLock, supplier);
    }

    protected <T> void execInLock(String keyLock, long timeLock, Supplier<T> supplier) {
        lockService.lock(keyLock, timeLock, supplier);
    }
}
