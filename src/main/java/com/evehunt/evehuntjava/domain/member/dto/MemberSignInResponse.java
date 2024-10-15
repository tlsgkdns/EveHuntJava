package com.evehunt.evehuntjava.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class MemberSignInResponse {
    private final String name;
    @NotNull
    private final String token;

    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getToken() {
        return this.token;
    }

    public MemberSignInResponse(String name, @NotNull String token) {
        this.name = name;
        this.token = token;
    }
}
