package com.evehunt.evehuntjava.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class MemberSignInRequest {
    @NotNull
    private final String email;
    @NotNull
    private final String password;

    @NotNull
    public final String getEmail() {
        return this.email;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public MemberSignInRequest(@NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
    }
}
