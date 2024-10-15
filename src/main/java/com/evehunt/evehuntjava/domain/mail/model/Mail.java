package com.evehunt.evehuntjava.domain.mail.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public final class Mail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private final Long id = null;
    @NotNull
    private String email;
    @NotNull
    private String title;
    @NotNull
    private String mailTxt;
    private boolean sent;

    public Mail() {}

    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final String getEmail() {
        return this.email;
    }

    public final void setEmail(@NotNull String var1) {
        this.email = var1;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(@NotNull String var1) {
        this.title = var1;
    }

    @NotNull
    public final String getMailTxt() {
        return this.mailTxt;
    }

    public final void setMailTxt(@NotNull String var1) {
        this.mailTxt = var1;
    }

    public final boolean getSent() {
        return this.sent;
    }

    public final void setSent(boolean var1) {
        this.sent = var1;
    }

    public Mail(@NotNull String email, @NotNull String title, @NotNull String mailTxt, boolean sent) {
        this.email = email;
        this.title = title;
        this.mailTxt = mailTxt;
        this.sent = sent;
    }
}