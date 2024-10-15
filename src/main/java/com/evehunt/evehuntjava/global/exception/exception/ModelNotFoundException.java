package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;

public final class ModelNotFoundException extends CustomException {
    public void log() {
        super.getLogger().error("대상 Entity를 찾지 못했습니다.");
        if (getMessage() != null) {
            super.getLogger().info(getMessage());
        }

    }

    public ModelNotFoundException(@NotNull String modelName, String findName) {
        super(modelName + " 에서 " + findName + " 을/를 찾을 수 없습니다.", ErrorCode.MODEL_NOT_FOUND);
    }
}