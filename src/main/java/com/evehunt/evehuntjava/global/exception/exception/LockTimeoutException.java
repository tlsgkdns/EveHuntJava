package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;

public class LockTimeoutException extends CustomException{

    public LockTimeoutException(String lockName) {
        super(lockName + "에서 Lock을 획득 실패하였습니다.", ErrorCode.REDIS_LOCK_TIMEOUT);
    }

    @Override
    public void log() {
        super.getLogger().error("Lock을 확득 실패하였습니다.");
        if(super.getMessage() != null)
        {
            super.getLogger().info(super.getMessage());
        }
    }
}
