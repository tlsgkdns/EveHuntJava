package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;

public final class AlreadyExistModelException extends CustomException {
    public void log() {
        super.getLogger().error("이미 존재합니다.");
        if (getMessage()!= null) {
            super.getLogger().info(getMessage());
        }

    }

    public AlreadyExistModelException(@NotNull String id) {
        super(id + "는 이미 존재합니다.", ErrorCode.ALREADY_EXIST_MODEL);
    }
}