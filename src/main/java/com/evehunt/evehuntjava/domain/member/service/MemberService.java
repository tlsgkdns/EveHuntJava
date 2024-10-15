package com.evehunt.evehuntjava.domain.member.service;

import com.evehunt.evehuntjava.domain.member.dto.*;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

public interface MemberService {
    @NotNull
    MemberResponse registerMember(@NotNull MemberRegisterRequest request);

    @NotNull
    MemberResponse registerAdmin(@NotNull MemberRegisterRequest request);

    @NotNull
    MemberSignInResponse signIn(@NotNull MemberSignInRequest request);

    @NotNull
    MemberResponse getMember(Long id);

    @NotNull
    MemberResponse editMember(Long id, @NotNull MemberEditRequest request);

    @NotNull
    MemberResponse getMember(String email);

    Long withdrawMember(Long id);

    void deleteAllMember();

    @NotNull
    PageResponse<ParticipateResponse> getParticipatedEvents(@NotNull PageRequest request, @NotNull String email);

    @NotNull
    MemberResponse editPassword(Long id, @NotNull MemberPasswordEditRequest request);

    @NotNull
    MemberResponse suspendMember(Long id, int days);

    @NotNull
    MemberResponse cancelSuspend(Long id);

    void cancelExpiredSuspend();
}