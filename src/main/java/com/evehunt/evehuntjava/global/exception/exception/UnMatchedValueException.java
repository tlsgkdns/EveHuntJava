package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;

public class UnMatchedValueException extends CustomException {
    @NotNull
    private final String target1;
    @NotNull
    private final String target2;

    public void log() {
        super.getLogger().error("값이 일치하지 않습니다.");
        super.log();
    }

    @NotNull
    public final String getTarget1() {
        return this.target1;
    }

    @NotNull
    public final String getTarget2() {
        return this.target2;
    }

    public UnMatchedValueException(@NotNull String target1, @NotNull String target2) {
        super(target1 + " 와 " + target2 + " 가/이 일치하지 않습니다.", ErrorCode.UNMATCHED_VALUE);
        this.target1 = target1;
        this.target2 = target2;
    }
}
