package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;

public class UnAuthorizedAccessException extends CustomException{

    public UnAuthorizedAccessException(String modelName) {
        super(modelName + "에 접근할 수 없습니다.", ErrorCode.UNAUTHORIZED_ACCESS);
    }

    @Override
    public void log() {
        super.getLogger().error("접근 권한이 없습니다.");
        super.log();
    }
}
