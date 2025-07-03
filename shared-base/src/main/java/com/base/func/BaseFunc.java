package com.base.func;

import com.base.BaseObjectLoggAble;
import com.base.service.LockService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.function.Supplier;

@Component
@NoArgsConstructor
@SuppressWarnings("unused")
public class BaseFunc extends BaseObjectLoggAble implements Serializable {

    @Autowired
    private LockService lockService;

    @Autowired(required = false)
    private TransactionTemplate transactionTemplate;

    protected <T> void execInLock(String keyLock, Supplier<T> supplier) {
        lockService.lock(keyLock, supplier);
    }

    protected <T> void execInLock(String keyLock, long timeLock, Supplier<T> supplier) {
        lockService.lock(keyLock, timeLock, supplier);
    }

    protected <T> T execInLockWithTransaction(String keyLock, Supplier<T> supplier) {
        return transactionTemplate.execute((status -> lockService.lock(keyLock, supplier)));
    }

    protected <T> T execInLockWithTransaction(String keyLock, long timeLock, Supplier<T> supplier) {
        return transactionTemplate.execute((status -> lockService.lock(keyLock, timeLock, supplier)));
    }

    protected <T> T execWithTransaction(Supplier<T> supplier) {
        return transactionTemplate.execute((status -> supplier.get()));
    }
}
