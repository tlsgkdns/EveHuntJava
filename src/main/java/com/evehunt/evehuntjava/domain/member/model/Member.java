package com.evehunt.evehuntjava.domain.member.model;

import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.global.common.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public final class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private final Long id = null;
    @ElementCollection(
            fetch = FetchType.LAZY
    )
    @NotNull
    private final Set<MemberType> roleSet = new HashSet<MemberType>();
    private ZonedDateTime suspendedTime;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private Image profileImage;

    public Member() {

    }

    @Nullable
    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final Set<MemberType> getRoleSet() {
        return this.roleSet;
    }

    @Nullable
    public final ZonedDateTime getSuspendedTime() {
        return this.suspendedTime;
    }

    public final void setSuspendedTime(@Nullable ZonedDateTime suspendedTime) {
        this.suspendedTime = suspendedTime;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(@NotNull String email) {
        this.email = email;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(@NotNull String password) {
        this.password = password;
    }

    public final Image getProfileImage() {
        return this.profileImage;
    }

    public final void setProfileImage(@Nullable Image image) {
        this.profileImage = image;
    }

    public Member(@NotNull String email, @NotNull String name, @NotNull String password, @Nullable Image profileImage) {
        super();
        this.email = email;
        this.name = name;
        this.password = password;
        this.profileImage = profileImage;
        this.roleSet.add(MemberType.USER);
    }
}