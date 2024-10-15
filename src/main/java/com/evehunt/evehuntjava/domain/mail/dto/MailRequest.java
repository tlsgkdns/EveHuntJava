package com.evehunt.evehuntjava.domain.mail.dto;

import com.evehunt.evehuntjava.domain.mail.model.Mail;

public class MailRequest {
    private final String email;

    private final String title;
    private final String content;

    public final Mail to() {
        return new Mail(this.email, this.title, this.content, false);
    }


    public final String getEmail() {
        return this.email;
    }


    public final String getTitle() {
        return this.title;
    }

    public final String getContent() {
        return this.content;
    }

    public MailRequest(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
