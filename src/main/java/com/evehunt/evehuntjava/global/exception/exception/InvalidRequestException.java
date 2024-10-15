package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;

public class InvalidRequestException extends CustomException {
    String modelName = "";
    public InvalidRequestException(String modelName, String messages) {
        super(messages, ErrorCode.INVALID_REQUEST);
        this.modelName = modelName;
    }
    @Override
    public void log()
    {
        super.getLogger().error(modelName + "에 적합하지 않은 요청입니다.");
        if(getMessage() != null)
            super.getLogger().info(getMessage());
    }
}
