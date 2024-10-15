package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;

public class InvalidModelException extends CustomException{
    private String modelName = "";

    public InvalidModelException(String modelName) {
        super("유효하지 않은 " + modelName + "입니다.", ErrorCode.REDIS_LOCK_TIMEOUT);
        this.modelName = modelName;
    }

    @Override
    public void log() {
        super.getLogger().error("유효하지 않은 " + modelName + "입니다.");
        if(super.getMessage() != null)
        {
            super.getLogger().info(super.getMessage());
        }
    }
}