package com.evehunt.evehuntjava.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MemberSignInResponse {
    private final String name;
    private final String token;


    public MemberSignInResponse(String name, @NotNull String token) {
        this.name = name;
        this.token = token;
    }
}
