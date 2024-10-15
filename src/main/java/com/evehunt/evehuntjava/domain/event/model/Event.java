package com.evehunt.evehuntjava.domain.event.model;

import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.global.common.BaseTimeEntity;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;

@Entity
public final class Event extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = null;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private Image image;
    private int capacity;
    @Enumerated(EnumType.STRING)
    @NotNull
    private EventStatus eventStatus;
    @NotNull
    private String winMessage;
    private String question;
    @NotNull
    private ZonedDateTime closeAt;
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @Nullable
    private Member host;

    @Nullable
    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull String var1) {
        this.title = var1;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@NotNull String var1) {
        this.description = var1;
    }

    @Nullable
    public final Image getImage() {
        return this.image;
    }

    public final void setImage(@Nullable Image var1) {
        this.image = var1;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final void setCapacity(int var1) {
        this.capacity = var1;
    }

    @NotNull
    public final EventStatus getEventStatus() {
        return this.eventStatus;
    }

    public final void setEventStatus(@NotNull EventStatus var1) {
        this.eventStatus = var1;
    }

    @NotNull
    public final String getWinMessage() {
        return this.winMessage;
    }

    public final void setWinMessage(@NotNull String var1) {
        this.winMessage = var1;
    }

    @Nullable
    public final String getQuestion() {
        return this.question;
    }

    public final void setQuestion(@Nullable String var1) {
        this.question = var1;
    }

    @NotNull
    public final ZonedDateTime getCloseAt() {
        return this.closeAt;
    }

    public final void setCloseAt(@NotNull ZonedDateTime var1) {
        this.closeAt = var1;
    }

    @Nullable
    public final Member getHost() {
        return this.host;
    }

    public final void setHost(@Nullable Member var1) {
        this.host = var1;
    }

    public Event(@NotNull String title, @NotNull String description, @Nullable Image image, int capacity, @NotNull EventStatus eventStatus, @NotNull String winMessage, @Nullable String question, @NotNull ZonedDateTime closeAt, @Nullable Member host) {
        super();
        this.title = title;
        this.description = description;
        this.image = image;
        this.capacity = capacity;
        this.eventStatus = eventStatus;
        this.winMessage = winMessage;
        this.question = question;
        this.closeAt = closeAt;
        this.host = host;
    }
    // $FF: synthetic method

    public Event() {
        super();
    }
}