package com.evehunt.evehuntjava.domain.image.dto;

import jakarta.validation.constraints.NotNull;

public class ImageResponse {
    @NotNull
    private final String uuid;
    private final String fileName;

    @NotNull
    public final String getLink() {
        return this.uuid + "_" + this.fileName;
    }

    @NotNull
    public final String getUuid() {
        return this.uuid;
    }

    public final String getFileName() {
        return this.fileName;
    }

    public ImageResponse(@NotNull String uuid, String fileName) {

        this.uuid = uuid;
        this.fileName = fileName;
    }
}
