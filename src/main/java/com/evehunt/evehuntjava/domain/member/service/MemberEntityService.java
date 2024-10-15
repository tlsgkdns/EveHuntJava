package com.evehunt.evehuntjava.domain.member.service;

import com.evehunt.evehuntjava.domain.member.dto.*;
import jakarta.validation.constraints.NotNull;

public interface MemberEntityService {
    @NotNull
    MemberResponse registerMember(@NotNull MemberRegisterRequest memberRegisterRequest);

    @NotNull
    MemberSignInResponse signIn(@NotNull MemberSignInRequest memberSignInRequest);

    @NotNull
    MemberResponse getMember(Long memberId);

    @NotNull
    MemberResponse editMember(Long id, @NotNull MemberEditRequest memberEditRequest);

    long withdrawMember(Long memberId);

    void deleteAllMember();

    @NotNull
    MemberResponse getMember(String email);

    @NotNull
    MemberResponse editPassword(Long memberId, @NotNull MemberPasswordEditRequest memberPasswordEditRequest);

    @NotNull
    MemberResponse addAdminAuthority(Long memberId);

    @NotNull
    MemberResponse suspendMember(Long memberId, int days);

    @NotNull
    MemberResponse cancelSuspend(Long memberId);

    void cancelExpiredSuspend();
}
