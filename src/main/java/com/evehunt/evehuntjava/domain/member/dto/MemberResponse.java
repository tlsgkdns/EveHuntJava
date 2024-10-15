package com.evehunt.evehuntjava.domain.member.dto;

import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.member.model.MemberType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

public class MemberResponse {
    @NotNull
    private final String email;
    private final Long memberId;
    private final String profileImageName;
    @NotNull
    private final String name;
    @NotNull
    private final LocalDateTime registeredDate;
    @NotNull
    private final LocalDateTime updatedDate;
    @NotNull
    private final Set<MemberType> role;

    private final LocalDateTime suspended;


    @NotNull
    public final String getEmail() {
        return this.email;
    }


    public final Long getMemberId() {
        return this.memberId;
    }


    public final String getProfileImageName() {
        return this.profileImageName;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final LocalDateTime getRegisteredDate() {
        return this.registeredDate;
    }

    @NotNull
    public final LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    @NotNull
    public final Set<MemberType> getRole() {
        return this.role;
    }

    public final LocalDateTime getSuspended() {
        return this.suspended;
    }

    public MemberResponse(@NotNull String email, Long memberId, String profileImageName, @NotNull String name, @NotNull LocalDateTime registeredDate, @NotNull LocalDateTime updatedDate, @NotNull Set<MemberType> role, LocalDateTime suspended) {
        this.email = email;
        this.memberId = memberId;
        this.profileImageName = profileImageName;
        this.name = name;
        this.registeredDate = registeredDate;
        this.updatedDate = updatedDate;
        this.role = role;
        this.suspended = suspended;
    }

    public static MemberResponse from(Member member)
    {
        return new MemberResponse(member.getEmail(), member.getId(), (member.getProfileImage() == null) ? null : member.getProfileImage().getLink(),
                member.getName(), member.getCreatedAt().toLocalDateTime(), member.getUpdatedAt().toLocalDateTime(),
                member.getRoleSet(), (member.getSuspendedTime() == null) ? null : member.getSuspendedTime().toLocalDateTime());
    }
}
