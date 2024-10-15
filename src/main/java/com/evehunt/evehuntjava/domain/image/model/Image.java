package com.evehunt.evehuntjava.domain.image.model;

import com.evehunt.evehuntjava.global.common.BaseTimeEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public final class Image extends BaseTimeEntity {
    @Id
    @NotNull
    private String uuid;
    private String filename;


    @NotNull
    public final String getLink() {
        return this.uuid + "_" + this.filename;
    }

    @NotNull
    public final String getUuid() {
        return this.uuid;
    }

    public final void setUuid(@NotNull String var1) {
        this.uuid = var1;
    }

    public Image(@NotNull String uuid, @NotNull String filename) {
        super();
        this.uuid = uuid;
        this.filename = filename;
    }

    // $FF: synthetic method

    public Image() {
        this((String)null, (String)null);
    }

    @Nullable
    public static Image from(@Nullable String imageName) {
        if (imageName == null) {
            return null;
        } else {
            String[] list = imageName.split("_");
            if (list.length < 2) {
                throw new RuntimeException();
            } else {
                return new Image(list[0], list[1]);
            }
        }
    }
}